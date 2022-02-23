package model.elements

import model.PathConverter
import model.SvgElement
import model.Transform
import model.style.Style

data class Ellipse(
    val cx: Double,
    val cy: Double,
    val rx: Double,
    val ry: Double,
    override var style: Style = Style(),
    override var transform: List<Transform> = emptyList()
) :
    SvgElement(
        style,
        transform
    ), PathConverter {

    companion object {
        const val NodeName = "ellipse"
    }

    override fun toPath(): Path {
        return Path(
            listOf(
                Path.Action.Move(cx - rx, cy),
                Path.Action.Arc(rx, ry, 0.0, 1, 0, rx * 2, 0.0, true),
                Path.Action.Arc(rx, ry, 0.0, 1, 0, -rx * 2, 0.0, true),
                Path.Action.Close
            ),
            style,
            transform
        ).getTransformedPath()
    }

    override fun toXml(): String {
        return "<$NodeName cx=\"$cx\" cy=\"$cy\" rx=\"$rx\" ry=\"$ry\" />"
    }
}