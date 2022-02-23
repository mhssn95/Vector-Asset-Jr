package model

class Matrix(val rowSize: Int, val columnSize: Int, private vararg val values: Double) {

    init {
        if (values.size != columnSize * rowSize) {
            throw IllegalArgumentException("incorrect matrix values")
        }
    }

    operator fun times(matrix: Matrix): Matrix {
        if (columnSize != matrix.rowSize) {
            throw IllegalArgumentException("can't multiply ($rowSize, $columnSize) matrix to (${matrix.rowSize}, ${matrix.columnSize}) matrix")
        }
        var row = 0
        val result = Array(rowSize * matrix.columnSize) {
            if (it != 0 && it % matrix.columnSize == 0) {
                row++
            }
            getRow(row).zip(matrix.getColumn(it % matrix.columnSize)) { r, c ->
                r * c
            }.sum()
        }
        return Matrix(rowSize, matrix.columnSize, *result.toDoubleArray())
    }

    operator fun get(row: Int): List<Double> {
        return getRow(row)
    }

    fun getRow(row: Int): List<Double> {
        return values.slice(row * columnSize until row * columnSize + columnSize)
    }

    fun getColumn(column: Int): List<Double> {
        return List(rowSize) {
            getRow(it)[column]
        }
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        repeat(rowSize) { row ->
            getRow(row).forEach {
                stringBuilder.append("$it\t")
            }
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }
}