package model.elements

import model.PathConverter
import model.SvgElement
import model.style.Style

data class Circle(val cx: Float, val cy: Float, val radius: Float, override var style: Style = Style()) :
    SvgElement(style), PathConverter {

    companion object {
        const val NodeName = "circle"
    }

    override fun toPath(): Path {
        return Path(
            listOf(
                Path.Action.Move(cx, cy),
                Path.Action.Move(-radius, 0f, true),
                Path.Action.Arc(radius, radius, 0f, 1, 1, radius * 2, 0f),
                Path.Action.Arc(radius, radius, 0f, 1, 1, -radius * 2, 0f)
            ),
            style
        )
    }

    override fun toXml(): String {
        return "<$NodeName cx=\"$cx\" cy=\"$cy\" r=\"$radius\" />"
    }
}