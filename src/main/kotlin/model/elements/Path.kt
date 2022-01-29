package model.elements

import model.PathConverter
import model.SvgElement
import model.style.Style

data class Path(val actions: List<Action>, override var style: Style = Style()) : SvgElement(style), PathConverter {

    companion object {
        const val NodeName = "path"
    }

    override fun toPath(): Path {
        return this
    }

    override fun toXml(): String {
        return "<$NodeName d=\"${
            actions.joinToString(" ") {
                "${it.symbol}${
                    when (it) {
                        is Action.Arc -> "${it.horizontalRadius},${it.verticalRadius},${it.degree},${it.isMoreThanHalf},${it.isPositiveArc},${it.x},${it.y}"
                        Action.Close -> ""
                        is Action.Curve -> "${it.x1},${it.y1},${it.x2},${it.y2},${it.x3},${it.y3}"
                        is Action.HorizontalLine -> "${it.dx}"
                        is Action.LineTo -> "${it.x},${it.y}"
                        is Action.Move -> "${it.x},${it.y}"
                        is Action.Quadratic -> "${it.x1},${it.y1},${it.x2},${it.y2}"
                        is Action.Smooth -> "${it.x1},${it.y1},${it.x2},${it.y2}"
                        is Action.SmoothQuadratic -> "${it.x},${it.y}"
                        is Action.VerticalLine -> "${it.dy}"
                    }
                }"
            }
        }\" />"
    }

    override fun toString(): String {
        return actions.joinToString("\n")
    }

    sealed class Action {
        abstract val symbol: Char

        data class Move(
            val x: Float,
            val y: Float,
            val relative: Boolean = false
        ) : Action() {
            override val symbol: Char
                get() = if (relative) 'm' else 'M'

            override fun toString(): String {
                return "${if (relative) "Relative " else ""}Move to ($x, $y)"
            }
        }

        data class HorizontalLine(
            val dx: Float,
            val relative: Boolean = false
        ) : Action() {
            override val symbol: Char
                get() = if (relative) 'h' else 'H'

            override fun toString(): String {
                return "${if (relative) "Relative " else ""}HL ($dx)"
            }
        }

        data class VerticalLine(
            val dy: Float,
            val relative: Boolean = false
        ) : Action() {
            override val symbol: Char
                get() = if (relative) 'v' else 'V'

            override fun toString(): String {
                return "${if (relative) "Relative " else ""}VL ($dy)"
            }
        }

        data class LineTo(
            val x: Float,
            val y: Float,
            val relative: Boolean = false
        ) : Action() {
            override val symbol: Char
                get() = if (relative) 'l' else 'L'

            override fun toString(): String {
                return "${if (relative) "Relative " else ""}Line ($x, $y)"
            }
        }

        data class Quadratic(
            val x1: Float,
            val y1: Float,
            val x2: Float,
            val y2: Float,
            val relative: Boolean = false
        ) : Action() {
            override val symbol: Char
                get() = if (relative) 'q' else 'Q'

            override fun toString(): String {
                return "${if (relative) "Relative " else ""}Quadratic ($x1, $y1) ($x2, $y2)"
            }
        }

        data class Curve(
            val x1: Float,
            val y1: Float,
            val x2: Float,
            val y2: Float,
            val x3: Float,
            val y3: Float,
            val relative: Boolean = false
        ) : Action() {
            override val symbol: Char
                get() = if (relative) 'c' else 'C'

            override fun toString(): String {
                return "${if (relative) "Relative " else ""}Curve ($x1, $y1) ($x2, $y2) ($x3, $y3)"
            }
        }

        data class Arc(
            val horizontalRadius: Float,
            val verticalRadius: Float,
            val degree: Float,
            val isMoreThanHalf: Int,
            val isPositiveArc: Int,
            val x: Float,
            val y: Float,
            val relative: Boolean = false
        ) : Action() {
            override val symbol: Char
                get() = if (relative) 'a' else 'A'

            override fun toString(): String {
                return "${if (relative) "Relative " else ""}Arc ($horizontalRadius, $verticalRadius) degree: $degree flag: $isMoreThanHalf ($x, $y)"
            }
        }

        data class Smooth(
            val x1: Float,
            val y1: Float,
            val x2: Float,
            val y2: Float,
            val relative: Boolean = false
        ) : Action() {
            override val symbol: Char
                get() = if (relative) 's' else 'S'

            override fun toString(): String {
                return "${if (relative) "Relative " else ""}Smooth ($x1, $y1) ($x2, $y2)"
            }
        }

        data class SmoothQuadratic(
            val x: Float,
            val y: Float,
            val relative: Boolean = false
        ) : Action() {
            override val symbol: Char
                get() = if (relative) 't' else 'T'

            override fun toString(): String {
                return "${if (relative) "Relative " else ""}Smooth Quadratic ($x, $y)"
            }
        }

        object Close : Action() {
            override val symbol: Char
                get() = 'z'

            override fun toString(): String {
                return "Close"
            }
        }
    }
}