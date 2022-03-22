package parser

import getAttr
import model.*
import model.elements.*
import model.style.Style
import model.style.brush.fill.SolidColor
import model.style.measure.MeasureUnits.Companion.dpOrNull
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.xml.sax.InputSource
import java.io.File
import java.io.InputStream
import java.io.StringReader
import java.lang.Exception
import javax.xml.parsers.DocumentBuilderFactory

class SvgParser {

    fun parseSvg(svg: File): Svg? {
        return parseSvg(svg.inputStream())
    }

    fun parseSvg(svg: String): Svg? {
        return parseSvg(svg.byteInputStream())
    }

    private fun parseSvg(inputStream: InputStream): Svg? {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        builder.setEntityResolver { _, _ ->
            InputSource(StringReader(""))
        }
        val document = builder.parse(inputStream)
        val node = getSvgNode(document) ?: return null
        var size = getSvgSize(node)
        val viewBox = getSvgViewBox(node) ?: size
        if (size == null) {
            size = viewBox
        }
        if (size == null) {
            throw IllegalArgumentException("invalid size")
        }
        val svg = Svg(size.width, size.height, viewBox!!.width, viewBox.height)
        fetch(node) {
            svg.addElement(it)
        }
        return svg
    }

    private fun fetch(node: Node, addElement: (SvgElement) -> Unit): SvgElement? {
        if (node.hasChildNodes()) {
            val child = node.firstChild
            when (child.nodeName) {
                Path.NodeName -> {
                    val styles = parseStyle(child)
                    val transform = parseTransform(child)
                    val actions = ArrayList<Path.Action>()
                    child.getAttr("")
                    child.getAttr("d")?.let { data ->
                        val pathReader = DataReader(data)
                        while (pathReader.hasData()) {
                            actions.add(pathReader.getAction())
                        }
                    }
                    val path = Path(actions, styles, transform)
                    addElement(path)
                }
                Polygon.NodeName -> {
                    val styles = parseStyle(child)
                    val transform = parseTransform(child)
                    val points = ArrayList<Point>()
                    child.getAttr("points")?.let { data ->
                        val pathData = DataReader(data)
                        while (pathData.hasData()) {
                            val x = pathData.readNextNumber()
                            val y = pathData.readNextNumber()
                            points.add(Point(x, y))
                        }
                    }
                    val polygon = Polygon(points, styles, transform)
                    addElement(polygon)
                }
                Polyline.NodeName -> {
                    val styles = parseStyle(child)
                    val transform = parseTransform(child)
                    val points = ArrayList<Point>()
                    child.getAttr("points")?.let { data ->
                        val pathData = DataReader(data)
                        while (pathData.hasData()) {
                            val x = pathData.readNextNumber()
                            val y = pathData.readNextNumber()
                            points.add(Point(x, y))
                        }
                        val polyline = Polyline(points, styles, transform)
                        addElement(polyline)
                    }
                }
                Circle.NodeName -> {
                    val styles = parseStyle(child)
                    val transform = parseTransform(child)
                    val cx = child.getAttr("cx")?.toDoubleOrNull()
                    val cy = child.getAttr("cy")?.toDoubleOrNull()
                    val r = child.getAttr("r")?.toDoubleOrNull()
                    if (cx == null || cy == null || r == null) {
                        throw IllegalArgumentException()
                    }
                    val circle = Circle(cx, cy, r, styles, transform)
                    addElement(circle)
                }
                Ellipse.NodeName -> {
                    val styles = parseStyle(child)
                    val transform = parseTransform(child)
                    val cx = child.getAttr("cx")?.toDoubleOrNull()
                    val cy = child.getAttr("cy")?.toDoubleOrNull()
                    val rx = child.getAttr("rx")?.toDoubleOrNull()
                    val ry = child.getAttr("ry")?.toDoubleOrNull()
                    if (cx == null || cy == null || rx == null || ry == null) {
                        throw IllegalArgumentException()
                    }
                    val ellipse = Ellipse(cx, cy, rx, ry, styles, transform)
                    addElement(ellipse)
                }
                Rectangle.NodeName -> {
                    val styles = parseStyle(child)
                    val transform = parseTransform(child)
                    val width = child.getAttr("width")?.toDoubleOrNull()
                    val height = child.getAttr("height")?.toDoubleOrNull()
                    val x = child.getAttr("x")?.toDoubleOrNull()
                    val y = child.getAttr("y")?.toDoubleOrNull()
                    val rx = child.getAttr("rx")?.toDoubleOrNull()
                    val ry = child.getAttr("ry")?.toDoubleOrNull()
                    if (width == null || height == null) {
                        throw  IllegalArgumentException()
                    }
                    val rectangle = Rectangle(
                        x = x,
                        y = y,
                        rx = rx,
                        ry = ry,
                        width = width,
                        height = height,
                        styles,
                        transform
                    )
                    addElement(rectangle)
                }
                Line.NodeName -> {
                    val styles = parseStyle(child)
                    val transform = parseTransform(child)
                    val x1 = child.getAttr("x1")?.toDoubleOrNull()
                    val y1 = child.getAttr("y1")?.toDoubleOrNull()
                    val x2 = child.getAttr("x2")?.toDoubleOrNull()
                    val y2 = child.getAttr("y2")?.toDoubleOrNull()
                    if (x1 == null || y1 == null || x2 == null || y2 == null) {
                        throw IllegalArgumentException()
                    }
                    val line = Line(x1, y1, x2, y2, styles, transform)
                    addElement(line)
                }
                Group.NodeName -> {
                    if (child.hasChildNodes()) {
                        val styles = parseStyle(child)
                        val transform = parseTransform(child)
                        val group = Group(styles, transform)
                        fetch(child) {
                            group.addElement(it)
                        }
                        addElement(group)
                    }
                }
            }
            node.removeChild(child)
            fetch(node, addElement)
        }
        return null
    }

    private fun parseStyle(node: Node): Style {
        val fillAttr = node.getAttr("fill")
        val fill = fillAttr?.let { SolidColor(fillAttr) }
        return Style(fill = fill, null)
    }

    private fun parseTransform(node: Node): List<Transform> {
        val transform = ArrayList<Transform>()
        node.getAttr("transform")?.let {
            val transformData = DataReader(it)
            while (transformData.hasData()) {
                transform.add(transformData.getTransform())
            }
        }
        return transform
    }

    private fun getSvgSize(node: Node): Size? {
        var width = node.getAttr("width")?.dpOrNull
        val height = node.getAttr("height")?.dpOrNull ?: width
        if (width == null) {
            width = height
        }
        if (width == null || width <= 0 || height!! <= 0) {
            return null
        }
        return Size(width, height)
    }

    private fun getSvgViewBox(node: Node): Size? {
        try {
            val viewBox = node.getAttr("viewBox") ?: return null
            val data = DataReader(viewBox)
            val l = data.readNextNumber()
            val t = data.readNextNumber()
            val r = data.readNextNumber()
            val b = data.readNextNumber()
            if (l < 0 || t < 0 || r < 0 || b < 0) {
                return null
            }
            return Size((r - l).let {
                if (it >= 0) {
                    it
                } else {
                    0.0
                }
            }, (b - t).let {
                if (it >= 0) {
                    it
                } else {
                    0.0
                }
            })
        } catch (e: Exception) {
            return null
        }
    }

    private fun getSvgNode(document: Document): Node? {
        val children = document.childNodes
        repeat(children.length) {
            if (children.item(it).nodeName == "svg" && (children.item(it).hasChildNodes() || children.length == 1)) {
                return children.item(it)
            }
        }
        return null
    }
}