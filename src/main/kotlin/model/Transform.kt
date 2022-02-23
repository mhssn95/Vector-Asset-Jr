package model

import kotlin.math.cos
import kotlin.math.sin

sealed class Transform {
    abstract fun transform(data: Array<Double>)

    class Matrix(val a: Double, val b: Double, val c: Double, val d: Double, val e: Double, val f: Double) :
        Transform() {
        override fun transform(data: Array<Double>) {
            val matrix = Matrix(
                rowSize = 3, columnSize = 3,
                a, b, c,
                d, e, f,
                0.0, 0.0, 1.0
            )
            data.pair { x, y, index ->
                val result = matrix * Matrix(
                    rowSize = 3, columnSize = 1,
                    x,
                    y,
                    1.0
                )
                data[index] = result[0][0]
                data[index + 1] = result[1][0]
            }
        }
    }

    class Translate(val x: Double, val y: Double) : Transform() {
        override fun transform(data: Array<Double>) {
            val matrix = Matrix(
                rowSize = 3, columnSize = 3,
                1.0, 0.0, x,
                0.0, 1.0, y,
                0.0, 0.0, 1.0
            )
            data.pair { x, y, index ->
                val result = matrix * Matrix(
                    rowSize = 3, columnSize = 1,
                    x,
                    y,
                    1.0
                )
                data[index] = result[0][0]
                data[index + 1] = result[1][0]
            }
        }
    }

    class Scale(private val scaleX: Double, private val scaleY: Double) : Transform() {
        override fun transform(data: Array<Double>) {
            val matrix = Matrix(
                rowSize = 3, columnSize = 3,
                scaleX, 0.0, 0.0,
                0.0, scaleY, 0.0,
                0.0, 0.0, 1.0
            )
            data.pair { x, y, index ->
                val result = matrix * Matrix(
                    rowSize = 3, columnSize = 1,
                    x,
                    y,
                    1.0
                )
                data[index] = result[0][0]
                data[index + 1] = result[1][0]
            }
        }
    }

    class Rotate(val a: Double, val x: Double, val y: Double) : Transform() {
        override fun transform(data: Array<Double>) {
            val radian = Math.toRadians(a)
            val rollbackToOriginMatrix = Matrix(
                rowSize = 3, columnSize = 3,
                1.0, 0.0, x,
                0.0, 1.0, y,
                0.0, 0.0, 1.0
            )
            val rotateMatrix = Matrix(
                rowSize = 3, columnSize = 3,
                cos(radian), -sin(radian), 0.0,
                sin(radian), cos(radian), 0.0,
                0.0, 0.0, 1.0
            )
            val changeOriginMatrix = Matrix(
                rowSize = 3, columnSize = 3,
                1.0, 0.0, -x,
                0.0, 1.0, -y,
                0.0, 0.0, 1.0
            )
            data.pair { x, y, index ->
                val result = rollbackToOriginMatrix * (rotateMatrix * (changeOriginMatrix * Matrix(
                    rowSize = 3, columnSize = 1,
                    x,
                    y,
                    1.0
                )))
                data[index] = result[0][0]
                data[index + 1] = result[1][0]
            }
        }
    }

    class SkewX(val a: Double) : Transform() {
        override fun transform(data: Array<Double>) {
            val matrix = Matrix(
                rowSize = 3, columnSize = 3,
                1.0, a, 0.0,
                0.0, 1.0, 0.0,
                0.0, 0.0, 1.0
            )
            data.pair { x, y, index ->
                val result = matrix * Matrix(
                    rowSize = 3, columnSize = 1,
                    x,
                    y,
                    1.0
                )
                data[index] = result[0][0]
                data[index + 1] = result[1][0]
            }
        }
    }

    class SkewY(val a: Double) : Transform() {
        override fun transform(data: Array<Double>) {
            val matrix = Matrix(
                rowSize = 3, columnSize = 3,
                1.0, 0.0, 0.0,
                a, 1.0, 0.0,
                0.0, 0.0, 1.0
            )
            data.pair { x, y, index ->
                val result = matrix * Matrix(
                    rowSize = 3, columnSize = 1,
                    x,
                    y,
                    1.0
                )
                data[index] = result[0][0]
                data[index + 1] = result[1][0]
            }
        }
    }
}

private fun <T> Array<T>.pair(pair: (a: T, b: T, index: Int) -> Unit) {
    var index = 0
    this.filterIndexed { index, _ ->
        index % 2 == 0
    }.zip(this.filterIndexed { index, _ -> index % 2 != 0 }) { a, b ->
        pair(a, b, index)
        index += 2
    }
}

