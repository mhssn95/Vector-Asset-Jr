package parser

import model.Transform
import model.elements.Path

data class DataReader(val data: String) {

    private val _data = data.replace(",", " ")
        .replace(Regex("(?<=\\d)\\s+(?=\\d|-)"), ",")
        .replace(Regex("\\s+"), "")
        .replace(Regex("(?<=(\\.\\d+))\\."), ",.")
        .replace(Regex("(?<=(\\d))-"), ",-")
        .replace(Regex("[\\(\\)]"), "")
        .replace("rotate", "r")
        .replace("translate", "n")
        .replace("skewX", "x")
        .replace("skewY", "y")
        .replace("scale", "e")
        .replace("matrix", "i")

    private var lastAction: Path.Action? = null
    private var pointer = 0

    fun hasData(): Boolean {
        return pointer < _data.length
    }

    fun getAction(): Path.Action {
        return when (val current = _data[pointer++]) {
            'm', 'M' -> {
                val action = readMove(current.isLowerCase())
                lastAction = action
                action
            }
            'v', 'V' -> {
                val action = readVerticalLine(current.isLowerCase())
                lastAction = action
                action
            }
            'h', 'H' -> {
                val action = readHorizontalLine(current.isLowerCase())
                lastAction = action
                action
            }
            'l', 'L' -> {
                val action = readLine(current.isLowerCase())
                lastAction = action
                action
            }
            'q', 'Q' -> {
                val action = readQuadratic(current.isLowerCase())
                lastAction = action
                action
            }
            'a', 'A' -> {
                val action = readArc(current.isLowerCase())
                lastAction = action
                action
            }
            'c', 'C' -> {
                val action = readCurve(current.isLowerCase())
                lastAction = action
                action
            }
            's', 'S' -> {
                val action = readSmooth(current.isLowerCase())
                lastAction = action
                action
            }
            't', 'T' -> {
                val action = readSmoothQuadratic(current.isLowerCase())
                lastAction = action
                action
            }
            'z', 'Z' -> {
                val action = Path.Action.Close
                lastAction = action
                action
            }
            ',' -> {
                return when (val action = lastAction) {
                    is Path.Action.HorizontalLine -> {
                        readHorizontalLine(action.relative)
                    }
                    is Path.Action.LineTo -> {
                        readLine(action.relative)
                    }
                    is Path.Action.Move -> {
                        readMove(action.relative)
                    }
                    is Path.Action.VerticalLine -> {
                        readVerticalLine(action.relative)
                    }
                    is Path.Action.Curve -> {
                        readCurve(action.relative)
                    }
                    is Path.Action.Arc -> {
                        readArc(action.relative)
                    }
                    is Path.Action.Quadratic -> {
                        readQuadratic(action.relative)
                    }
                    is Path.Action.Smooth -> {
                        readSmooth(action.relative)
                    }
                    is Path.Action.SmoothQuadratic -> {
                        readSmoothQuadratic(action.relative)
                    }
                    else -> throw IllegalArgumentException()
                }
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    fun getTransform(): Transform {
        return when (val current = _data[pointer++]) {
            //rotate
            'r' -> {
                val a = readNextNumber()
                val x = readNextNumber(0.0)
                val y = readNextNumber(0.0, true)
                Transform.Rotate(a, x, y)
            }
            //translate
            'n' -> {
                val x = readNextNumber()
                val y = readNextNumber(0.0, true)
                Transform.Translate(x, y)
            }
            //skewX
            'x' -> {
                val a = readNextNumber(isLastNumber = true)
                Transform.SkewX(a)
            }
            //skewY
            'y' -> {
                val a = readNextNumber(isLastNumber = true)
                Transform.SkewY(a)
            }
            //scale
            'e' -> {
                val x = readNextNumber()
                val y = readNextNumber(x, true)
                Transform.Scale(x, y)
            }
            //matrix
            'i' -> {
                val a = readNextNumber()
                val b = readNextNumber()
                val c = readNextNumber()
                val d = readNextNumber()
                val e = readNextNumber()
                val f = readNextNumber()
                Transform.Matrix(a, b, c, d, e, f)
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    private fun readMove(relative: Boolean): Path.Action.Move {
        val x = readNextNumber()
        val y = readNextNumber(isLastNumber = true)
        return Path.Action.Move(x, y, relative)
    }

    private fun readVerticalLine(relative: Boolean): Path.Action.VerticalLine {
        val dy = readNextNumber(isLastNumber = true)
        return Path.Action.VerticalLine(dy, relative)
    }

    private fun readHorizontalLine(relative: Boolean): Path.Action.HorizontalLine {
        val dx = readNextNumber(isLastNumber = true)
        return Path.Action.HorizontalLine(dx, relative)
    }

    private fun readLine(relative: Boolean): Path.Action.LineTo {
        val x = readNextNumber()
        val y = readNextNumber(isLastNumber = true)
        return Path.Action.LineTo(x, y, relative)
    }

    private fun readQuadratic(relative: Boolean): Path.Action.Quadratic {
        val x1 = readNextNumber()
        val y1 = readNextNumber()
        val x2 = readNextNumber()
        val y2 = readNextNumber(isLastNumber = true)
        return Path.Action.Quadratic(x1, y1, x2, y2, relative)
    }

    private fun readCurve(relative: Boolean): Path.Action.Curve {
        val x1 = readNextNumber()
        val y1 = readNextNumber()
        val x2 = readNextNumber()
        val y2 = readNextNumber()
        val x3 = readNextNumber()
        val y3 = readNextNumber(isLastNumber = true)
        return Path.Action.Curve(x1, y1, x2, y2, x3, y3, relative)
    }

    private fun readArc(relative: Boolean): Path.Action.Arc {
        val x1 = readNextNumber()
        val y1 = readNextNumber()
        val degree = readNextNumber()
        val flag = readNextNumber().toInt()
        val sweep = readNextNumber().toInt()
        val x2 = readNextNumber()
        val y2 = readNextNumber(isLastNumber = true)
        return Path.Action.Arc(x1, y1, degree, flag, sweep, x2, y2, relative)
    }

    private fun readSmooth(relative: Boolean): Path.Action.Smooth {
        val x1 = readNextNumber()
        val y1 = readNextNumber()
        val x2 = readNextNumber()
        val y2 = readNextNumber(isLastNumber = true)
        return Path.Action.Smooth(x1, y1, x2, y2, relative)
    }

    private fun readSmoothQuadratic(relative: Boolean): Path.Action.SmoothQuadratic {
        val x = readNextNumber()
        val y = readNextNumber(isLastNumber = true)
        return Path.Action.SmoothQuadratic(x, y, relative)
    }

    internal fun readNextNumber(default: Double? = null, isLastNumber: Boolean = false): Double {
        val stringBuilder = StringBuilder()
        while (hasData() && numbers.contains(_data[pointer])) {
            stringBuilder.append(_data[pointer])
            pointer++
        }
        if (stringBuilder.isEmpty()) {
            return default ?: throw IllegalArgumentException()
        }
        if (stringBuilder.toString() == "-") {
            throw IllegalArgumentException()
        }
        if (!isLastNumber) {
            pointer++
        }
        return stringBuilder.toString().toDouble()
    }

    override fun toString(): String {
        return _data
    }

    companion object {
        private val numbers = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '.')
    }
}