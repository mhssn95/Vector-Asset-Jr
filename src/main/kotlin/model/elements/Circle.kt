package model.elements

import model.PathConverter
import model.SvgElement
import model.Transform
import model.style.Style

data class Circle(
    val cx: Double,
    val cy: Double,
    val radius: Double,
    override var style: Style = Style(),
    override var transform: List<Transform> = emptyList()
) :
    SvgElement(style, transform), PathConverter {

    companion object {
        const val NodeName = "circle"
    }

    override fun toPath(): Path {
        return Path(
            listOf(
                Path.Action.Move(cx, cy),
                Path.Action.Move(-radius, 0.0, true),
                Path.Action.Arc(radius, radius, 0.0, 1, 1, radius * 2, 0.0),
                Path.Action.Arc(radius, radius, 0.0, 1, 1, -radius * 2, 0.0)
            ),
            style,
            transform
        ).getTransformedPath()
    }

    override fun toXml(): String {
        return "<$NodeName cx=\"$cx\" cy=\"$cy\" r=\"$radius\" />"
    }
}