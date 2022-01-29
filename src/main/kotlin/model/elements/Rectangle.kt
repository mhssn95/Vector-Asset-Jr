package model.elements

import model.PathConverter
import model.SvgElement
import model.style.Style

data class Rectangle(
    val x: Float? = null,
    val y: Float? = null,
    val rx: Float? = null,
    val ry: Float? = null,
    val width: Float,
    val height: Float, override var style: Style
) : SvgElement(style), PathConverter {

    companion object {
        const val NodeName = "rect"
    }

    override fun toPath(): Path {
        val actions = ArrayList<Path.Action>()
        val x = x ?: 0f
        val y = y ?: 0f
        val isRounded = rx != null || ry != null
        if (isRounded) {
            val rx = rx ?: ry ?: 0f
            val ry = ry ?: rx
            actions.add(Path.Action.Move(x + rx, y))
            actions.add(Path.Action.LineTo(x + width - rx, y))
            actions.add(Path.Action.Arc(rx, ry, 0f, 0, 1, x + width, y + ry))
            actions.add(Path.Action.LineTo(x + width, y + height - ry))
            actions.add(Path.Action.Arc(rx, ry, 0f, 0, 1, x + width - rx, y + height))
            actions.add(Path.Action.LineTo(x + rx, y + height))
            actions.add(Path.Action.Arc(rx, ry, 0f, 0, 1, x, y + height - ry))
            actions.add(Path.Action.LineTo(x, y + ry))
            actions.add(Path.Action.Arc(rx, ry, 0f, 0, 1, x + rx, y))
        } else {
            actions.add(Path.Action.Move(x, y))
            actions.add(Path.Action.HorizontalLine(width, true))
            actions.add((Path.Action.VerticalLine(height, true)))
            actions.add((Path.Action.HorizontalLine(-width, true)))
        }
        actions.add(Path.Action.Close)
        return Path(actions, style)
    }

    override fun toXml(): String {
        return "<$NodeName${x?.let { " x=\"$it\"" } ?: ""}${y?.let { " y=\"$it\"" } ?: ""}${rx?.let { " rx=\"$it\"" } ?: ""}${ry?.let { " ry=\"$it\"" } ?: ""} width=\"$width\" height=\"$height\" />"
    }
}