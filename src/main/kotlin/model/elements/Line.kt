package model.elements

import model.PathConverter
import model.SvgElement
import model.style.Style

data class Line(val x1: Float, val y1: Float, val x2: Float, val y2: Float, override var style: Style) :
    SvgElement(
        style
    ), PathConverter {

    companion object {
        const val NodeName = "line"
    }

    override fun toPath(): Path {
        return Path(
            listOf(
                Path.Action.Move(x1, y1),
                Path.Action.LineTo(x2, y2)
            ),
            style
        )
    }

    override fun toXml(): String {
        return "<$NodeName x1=\"$x1\" y1=\"$y1\" x2=\"$x2\" y2=\"$y2\"/>"
    }
}