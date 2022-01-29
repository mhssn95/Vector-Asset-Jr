package parser

import getAttr
import model.Point
import model.Svg
import model.SvgElement
import model.elements.*
import model.style.Style
import model.style.brush.fill.SolidColor
import org.w3c.dom.Node
import java.io.File
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

class SvgParser {

    fun parseSvg(svg: File): Svg {
        return parseSvg(svg.inputStream())
    }

    fun parseSvg(svg: String): Svg {
        return parseSvg(svg.byteInputStream())
    }

    private fun parseSvg(inputStream: InputStream): Svg {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val document = builder.parse(inputStream)
        val node = document.firstChild
        val svg = Svg(512f, 512f, 512f, 512f)
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
                    child.getAttr("d")?.let { data ->
                        val pathData = PathData(data)
                        val actions = ArrayList<Path.Action>()
                        while (pathData.hasData()) {
                            actions.add(pathData.getAction())
                        }
                        val path = Path(actions, styles)
                        addElement(path)
                    }
                }
                Polygon.NodeName -> {
                    val styles = parseStyle(child)
                    child.getAttr("points")?.let { data ->
                        val points = ArrayList<Point>()
                        val pathData = PathData(data)
                        while (pathData.hasData()) {
                            val x = pathData.readNextNumber()
                            val y = pathData.readNextNumber()
                            points.add(Point(x, y))
                        }
                        val polygon = Polygon(points, styles)
                        addElement(polygon)
                    }
                }
                Polyline.NodeName -> {
                    val styles = parseStyle(child)
                    child.getAttr("points")?.let { data ->
                        val points = ArrayList<Point>()
                        val pathData = PathData(data)
                        while (pathData.hasData()) {
                            val x = pathData.readNextNumber()
                            val y = pathData.readNextNumber()
                            points.add(Point(x, y))
                        }
                        val polyline = Polyline(points, styles)
                        addElement(polyline)
                    }
                }
                Circle.NodeName -> {
                    val styles = parseStyle(child)
                    val cx = child.getAttr("cx")?.toFloatOrNull()
                    val cy = child.getAttr("cy")?.toFloatOrNull()
                    val r = child.getAttr("r")?.toFloatOrNull()
                    if (cx == null || cy == null || r == null) {
                        throw IllegalArgumentException()
                    }
                    val circle = Circle(cx, cy, r, styles)
                    addElement(circle)
                }
                Ellipse.NodeName -> {
                    val styles = parseStyle(child)
                    val cx = child.getAttr("cx")?.toFloatOrNull()
                    val cy = child.getAttr("cy")?.toFloatOrNull()
                    val rx = child.getAttr("rx")?.toFloatOrNull()
                    val ry = child.getAttr("ry")?.toFloatOrNull()
                    if (cx == null || cy == null || rx == null || ry == null) {
                        throw IllegalArgumentException()
                    }
                    val ellipse = Ellipse(cx, cy, rx, ry, styles)
                    addElement(ellipse)
                }
                Rectangle.NodeName -> {
                    val styles = parseStyle(child)
                    val width = child.getAttr("width")?.toFloatOrNull()
                    val height = child.getAttr("height")?.toFloatOrNull()
                    val x = child.getAttr("x")?.toFloatOrNull()
                    val y = child.getAttr("y")?.toFloatOrNull()
                    val rx = child.getAttr("rx")?.toFloatOrNull()
                    val ry = child.getAttr("ry")?.toFloatOrNull()
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
                        styles
                    )
                    addElement(rectangle)
                }
                Line.NodeName -> {
                    val styles = parseStyle(child)
                    val x1 = child.getAttr("x1")?.toFloatOrNull()
                    val y1 = child.getAttr("y1")?.toFloatOrNull()
                    val x2 = child.getAttr("x2")?.toFloatOrNull()
                    val y2 = child.getAttr("y2")?.toFloatOrNull()
                    if (x1 == null || y1 == null || x2 == null || y2 == null) {
                        throw IllegalArgumentException()
                    }
                    val line = Line(x1, y1, x2, y2, styles)
                    addElement(line)
                }
                Group.NodeName -> {
                    if (child.hasChildNodes()) {
                        val styles = parseStyle(child)
                        val group = Group(styles)
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
}