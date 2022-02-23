package model.elements

import model.PathConverter
import model.SvgElement
import model.Transform
import model.style.Style

data class Line(
    val x1: Double,
    val y1: Double,
    val x2: Double,
    val y2: Double,
    override var style: Style = Style(),
    override var transform: List<Transform> = emptyList()
) :
    SvgElement(
        style,
        transform
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
            style,
            transform
        ).getTransformedPath()
    }

    override fun toXml(): String {
        return "<$NodeName x1=\"$x1\" y1=\"$y1\" x2=\"$x2\" y2=\"$y2\"/>"
    }
}