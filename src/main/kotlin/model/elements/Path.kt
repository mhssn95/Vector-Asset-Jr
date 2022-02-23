package model.elements

import model.PathConverter
import model.Point
import model.SvgElement
import model.Transform
import model.style.Style
import kotlin.math.ceil

data class Path(
    val actions: List<Action>,
    override var style: Style = Style(),
    override var transform: List<Transform> = emptyList()
) : SvgElement(style, transform), PathConverter {

    companion object {
        const val NodeName = "path"
    }

    override fun toPath(): Path {
        return getTransformedPath()
    }

    fun getTransformedPath(): Path {
        val actions = this.actions.toTypedArray()
        transform.reversed().forEach { transform ->
            actions.forEachIndexed { index, action ->
                val relative = action.relative
                when (action) {
                    is Action.Arc -> {

                    }
                    Action.Close -> {

                    }
                    else -> {
                        val data = action.toData()
                        transform.transform(data)
                        actions[index] = action.fromData(data, relative)
                    }
                }
            }
        }
        return Path(actions.toList(), style, transform)
    }

    override fun toXml(): String {
        return "<$NodeName d=\"${
            actions.joinToString(" ") {
                "${it.symbol}${
                    when (it) {
                        is Action.Move -> "${it.x},${it.y}"
                        is Action.LineTo -> "${it.x},${it.y}"
                        is Action.HorizontalLine -> "${it.dx}"
                        is Action.VerticalLine -> "${it.dy}"
                        is Action.Curve -> "${it.x1},${it.y1},${it.x2},${it.y2},${it.x3},${it.y3}"
                        is Action.Smooth -> "${it.x1},${it.y1},${it.x2},${it.y2}"
                        is Action.Arc -> "${it.horizontalRadius},${it.verticalRadius},${it.degree},${it.largeArc},${it.isPositiveArc},${it.x},${it.y}"
                        is Action.Quadratic -> "${it.x1},${it.y1},${it.x2},${it.y2}"
                        is Action.SmoothQuadratic -> "${it.x},${it.y}"
                        Action.Close -> ""
                    }
                }"
            }
        }\" />"
    }

    override fun toString(): String {
        return actions.joinToString("\n")
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Path) {
            return false
        }
        return actions == other.actions && style == other.style && transform == other.transform
    }

    sealed class Action {
        abstract val symbol: Char
        abstract val relative: Boolean

        abstract fun fromData(data: Array<Double>, relative: Boolean): Action
        abstract fun toData(): Array<Double>

        data class Move(
            val x: Double,
            val y: Double,
            override val relative: Boolean = false
        ) : Action() {
            override val symbol: Char
                get() = if (relative) 'm' else 'M'

            override fun fromData(data: Array<Double>, relative: Boolean): Action {
                return Move(data[0], data[1], relative)
            }

            override fun toData(): Array<Double> {
                return arrayOf(x, y)
            }

            override fun toString(): String {
                return "${if (relative) "Relative " else ""}Move to ($x, $y)"
            }
        }

        data class HorizontalLine(
            val dx: Double,
            override val relative: Boolean = false
        ) : Action() {
            override val symbol: Char
                get() = if (relative) 'h' else 'H'

            override fun fromData(data: Array<Double>, relative: Boolean): Action {
                return HorizontalLine(data[0], relative)
            }

            override fun toData(): Array<Double> {
                return arrayOf(dx)
            }

            override fun toString(): String {
                return "${if (relative) "Relative " else ""}HL ($dx)"
            }
        }

        data class VerticalLine(
            val dy: Double,
            override val relative: Boolean = false
        ) : Action() {
            override val symbol: Char
                get() = if (relative) 'v' else 'V'

            override fun fromData(data: Array<Double>, relative: Boolean): Action {
                return VerticalLine(data[0], relative)
            }

            override fun toData(): Array<Double> {
                return arrayOf(dy)
            }

            override fun toString(): String {
                return "${if (relative) "Relative " else ""}VL ($dy)"
            }
        }

        data class LineTo(
            val x: Double,
            val y: Double,
            override val relative: Boolean = false
        ) : Action() {
            override val symbol: Char
                get() = if (relative) 'l' else 'L'

            override fun fromData(data: Array<Double>, relative: Boolean): Action {
                return LineTo(data[0], data[1], relative)
            }

            override fun toData(): Array<Double> {
                return arrayOf(x, y)
            }

            override fun toString(): String {
                return "${if (relative) "Relative " else ""}Line ($x, $y)"
            }
        }

        data class Curve(
            val x1: Double,
            val y1: Double,
            val x2: Double,
            val y2: Double,
            val x3: Double,
            val y3: Double,
            override val relative: Boolean = false
        ) : Action() {
            override val symbol: Char
                get() = if (relative) 'c' else 'C'

            override fun fromData(data: Array<Double>, relative: Boolean): Action {
                return Curve(data[0], data[1], data[2], data[3], data[4], data[5], relative)
            }

            override fun toData(): Array<Double> {
                return arrayOf(x1, y1, x2, y2, x3, y3)
            }

            override fun toString(): String {
                return "${if (relative) "Relative " else ""}Curve ($x1, $y1) ($x2, $y2) ($x3, $y3)"
            }
        }

        data class Smooth(
            val x1: Double,
            val y1: Double,
            val x2: Double,
            val y2: Double,
            override val relative: Boolean = false
        ) : Action() {
            override val symbol: Char
                get() = if (relative) 's' else 'S'

            override fun fromData(data: Array<Double>, relative: Boolean): Action {
                return Smooth(data[0], data[1], data[2], data[3], relative)
            }

            override fun toData(): Array<Double> {
                return arrayOf(x1, y1, x2, y2)
            }

            override fun toString(): String {
                return "${if (relative) "Relative " else ""}Smooth ($x1, $y1) ($x2, $y2)"
            }
        }

        data class Quadratic(
            val x1: Double,
            val y1: Double,
            val x2: Double,
            val y2: Double,
            override val relative: Boolean = false
        ) : Action() {
            override val symbol: Char
                get() = if (relative) 'q' else 'Q'

            override fun fromData(data: Array<Double>, relative: Boolean): Action {
                return Quadratic(data[0], data[1], data[2], data[3], relative)
            }

            override fun toData(): Array<Double> {
                return arrayOf(x1, y1, x2, y2)
            }

            override fun toString(): String {
                return "${if (relative) "Relative " else ""}Quadratic ($x1, $y1) ($x2, $y2)"
            }
        }

        data class SmoothQuadratic(
            val x: Double,
            val y: Double,
            override val relative: Boolean = false
        ) : Action() {
            override val symbol: Char
                get() = if (relative) 't' else 'T'

            override fun fromData(data: Array<Double>, relative: Boolean): Action {
                return SmoothQuadratic(x, y, relative)
            }

            override fun toData(): Array<Double> {
                return arrayOf(x, y)
            }

            override fun toString(): String {
                return "${if (relative) "Relative " else ""}Smooth Quadratic ($x, $y)"
            }
        }

        data class Arc(
            val horizontalRadius: Double,
            val verticalRadius: Double,
            val degree: Double,
            val largeArc: Int,
            val isPositiveArc: Int,
            val x: Double,
            val y: Double,
            override val relative: Boolean = false
        ) : Action() {
            override val symbol: Char
                get() = if (relative) 'a' else 'A'

            override fun fromData(data: Array<Double>, relative: Boolean): Action {
                return Arc(data[0], data[1], data[2], data[3].toInt(), data[4].toInt(), data[5], data[6], relative)
            }

            override fun toData(): Array<Double> {
                return arrayOf(
                    horizontalRadius,
                    verticalRadius,
                    degree,
                    largeArc.toDouble(),
                    isPositiveArc.toDouble(),
                    x,
                    y
                )
            }

            override fun toString(): String {
                return "${if (relative) "Relative " else ""}Arc ($horizontalRadius, $verticalRadius) degree: $degree flag: $largeArc ($x, $y)"
            }
        }

        object Close : Action() {
            override val symbol: Char
                get() = 'z'
            override val relative: Boolean
                get() = false

            override fun fromData(data: Array<Double>, relative: Boolean): Action {
                return Close
            }

            override fun toData(): Array<Double> {
                return emptyArray()
            }

            override fun toString(): String {
                return "Close"
            }
        }
    }
}