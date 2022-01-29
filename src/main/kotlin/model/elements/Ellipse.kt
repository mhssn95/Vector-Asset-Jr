package model.elements

import model.PathConverter
import model.SvgElement
import model.style.Style

data class Ellipse(val cx: Float, val cy: Float, val rx: Float, val ry: Float, override var style: Style) :
    SvgElement(
        style
    ), PathConverter {

    companion object {
        const val NodeName = "ellipse"
    }

    override fun toPath(): Path {
        return Path(
            listOf(
                Path.Action.Move(cx - rx, cy),
                Path.Action.Arc(rx, ry, 0f, 1, 0, rx * 2, 0f, true),
                Path.Action.Arc(rx, ry, 0f, 1, 0, -rx * 2, 0f, true),
                Path.Action.Close
            ),
            style
        )
    }

    override fun toXml(): String {
        return "<$NodeName cx=\"$cx\" cy=\"$cy\" rx=\"$rx\" ry=\"$ry\" />"
    }
}