package model.style.measure

/**
 * [MeasureUnits] is a helper class that converts units to PX values.
 * @see <a href="https://oreillymedia.github.io/Using_SVG/guide/units.html">Units for Measurements</a>
 */
enum class MeasureUnits(val unit: String, val value: Double) {
    /**
     * [PX] is the default value
     */
    PX("px", 1.0),

    /**
     * [PT] is Points.
     * 1pt ≅ 1.3333px or user units.
     */
    PT("pt", 0.75),

    /**
     * [IN] is Inches.
     * 1in = 96px or user units
     */
    IN("in", 0.01041666667),

    /**
     * [CM] is Centimeters.
     * 1cm ≅ 37.795px or user units
     */
    CM("cm", 0.02645852626),

    /**
     * [PC] is Picas.
     * 1pc = 16px or user units
     */
    PC("pc", 0.0625),

    /**
     * [MM] is Millimeters.
     * 1mm ≅ 3.7795px or user units
     */
    MM("mm", 0.2645852626);


    companion object {
        /**
         * Create a [Double] using a [String]:
         *    val width = "500pt"
         *    val pxWidth = width.dpOrNull
         *    // -- or --
         *    val height = "500pt".dpOrNull
         *
         * @throws [IllegalArgumentException] if it match the [REGEX] and unit not implemented by this class
         */

        inline val String.dpOrNull: Double?
            get() {
                val regex = "^\\d+[a-z]{2}$"
                var size = this.toDoubleOrNull()

                if (size != null) {
                    return size
                }

                if (this.matches(Regex(regex))) {
                    val unit = this.takeLast(2)
                    size = this.dropLast(2).toDouble()

                    val converter = values().find {
                        it.unit == unit
                    } ?: throw IllegalArgumentException("Unsupported unit")

                    size /= converter.value
                } else {
                    return null
                }

                return size
            }
    }
}