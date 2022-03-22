import model.style.measure.MeasureUnits.Companion.dpOrNull
import org.junit.Assert
import org.junit.Test

class MeasureUnits {

    @Test(expected = IllegalArgumentException::class)
    fun `convert unsupported unit`() {
        val width = "400pv"

        width.dpOrNull
    }

    @Test
    fun `convert unknown unit`() {
        val width = ""

        Assert.assertNull(width.dpOrNull)
    }

    @Test
    fun `convert px to px`() {
        val width = "500"

        Assert.assertEquals(width.dpOrNull, 500.0)
    }

    @Test
    fun `convert units to px`() {
        val delta = 1e-15
        val values = listOf(
            "1pt" to 1.333,
            "1in" to 96.0,
            "1mm" to 3.78,
            "1cm" to 37.795,
            "1pc" to 16.0,
            "1px" to 1.0
        )

        values.forEach {
            val value = String.format("%.3f",it.first.dpOrNull).toDouble()
            Assert.assertEquals(it.second, value, delta)
        }
    }
}