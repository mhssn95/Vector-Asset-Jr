import ext.nextDouble
import utils.ElementsUtils.randomRect
import model.Svg
import model.elements.Path
import org.junit.Test
import parser.SvgParser
import java.lang.IllegalArgumentException
import kotlin.random.Random

class RectangleParser {

    private val svgParser = SvgParser()

    @Test
    fun `parse rectangle without radius`() {
        val a = Svg(Random.nextDouble(255), Random.nextDouble(255), Random.nextDouble(255), Random.nextDouble(255))
        val rect = randomRect(radiusEnabled = false, withTransform = false)
        a.addElement(rect)
        val svgText = a.toString()
        val b = svgParser.parseSvg(svgText)
        assert(b?.elements?.contains(rect) ?: false)
    }

    @Test
    fun `parse rectangle with both radius`() {
        val a = Svg(Random.nextDouble(255), Random.nextDouble(255), Random.nextDouble(255), Random.nextDouble(255))
        val rect = randomRect(radiusEnabled = true, withTransform = false)
        a.addElement(rect)
        val svgText = a.toString()
        val b = svgParser.parseSvg(svgText)
        assert(b?.elements?.contains(rect) ?: false)
    }

    @Test
    fun `parse rectangle with rx radius`() {
        val a = Svg(Random.nextDouble(255), Random.nextDouble(255), Random.nextDouble(255), Random.nextDouble(255))
        val rect = randomRect(ry = null, radiusEnabled = true, withTransform = false)
        a.addElement(rect)
        val svgText = a.toString()
        val b = svgParser.parseSvg(svgText)
        assert(b?.elements?.contains(rect) ?: false)
    }

    @Test
    fun `parse rectangle with ry radius`() {
        val a = Svg(Random.nextDouble(255), Random.nextDouble(255), Random.nextDouble(255), Random.nextDouble(255))
        val rect = randomRect(rx = null, radiusEnabled = true, withTransform = false)
        a.addElement(rect)
        val svgText = a.toString()
        val b = svgParser.parseSvg(svgText)
        assert(b?.elements?.contains(rect) ?: false)
    }

    @Test
    fun `parse rectangle with x only`() {
        val a = Svg(Random.nextDouble(255), Random.nextDouble(255), Random.nextDouble(255), Random.nextDouble(255))
        val rect = randomRect(y = null, xyEnabled = true, withTransform = false)
        a.addElement(rect)
        val svgText = a.toString()
        val b = svgParser.parseSvg(svgText)
        assert(b?.elements?.contains(rect) ?: false)
    }

    @Test
    fun `parse rectangle with y only`() {
        val a = Svg(Random.nextDouble(255), Random.nextDouble(255), Random.nextDouble(255), Random.nextDouble(255))
        val rect = randomRect(x = null, xyEnabled = true, withTransform = false)
        a.addElement(rect)
        val svgText = a.toString()
        val b = svgParser.parseSvg(svgText)
        assert(b?.elements?.contains(rect) ?: false)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse rectangle without width`() {
        val svg = """
            <svg>
                <rect x="${Random.nextDouble(512)}" y="${Random.nextDouble(512)}" height="${Random.nextDouble(512)}" />
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svg)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse rectangle without height`() {
        val svg = """
            <svg>
                <rect x="${Random.nextDouble(512)}" y="${Random.nextDouble(512)}" width="${Random.nextDouble(512)}" />
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svg)
    }

    @Test
    fun `convert rect to path without radius`() {
        val rect = randomRect(radiusEnabled = false, withTransform = false)
        val x = rect.x ?: 0.0
        val y = rect.y ?: 0.0
        val path = Path(
            listOf(
                Path.Action.Move(x, y),
                Path.Action.HorizontalLine(rect.width, true),
                Path.Action.VerticalLine(rect.height, true),
                Path.Action.HorizontalLine(-rect.width, true),
                Path.Action.Close
            )
        )
        assert(rect.toPath() == path)
    }

    @Test
    fun `convert rect to path with radius`() {
        val rect = randomRect(radiusEnabled = true, withTransform = false)
        val x = rect.x ?: 0.0
        val y = rect.y ?: 0.0
        val rx = rect.rx ?: 0.0
        val ry = rect.ry ?: 0.0
        val path = Path(
            listOf(
                Path.Action.Move(x + rx, y),
                Path.Action.LineTo(x + rect.width - rx, y),
                Path.Action.Arc(rx, ry, 0.0, 0, 1, x + rect.width, y + ry),
                Path.Action.LineTo(x + rect.width, y + rect.height - ry),
                Path.Action.Arc(rx, ry, 0.0, 0, 1, x + rect.width - rx, y + rect.height),
                Path.Action.LineTo(x + rx, y + rect.height),
                Path.Action.Arc(rx, ry, 0.0, 0, 1, x, y + rect.height - ry),
                Path.Action.LineTo(x, y + ry),
                Path.Action.Arc(rx, ry, 0.0, 0, 1, x + rx, y),
                Path.Action.Close
            )
        )
        assert(rect.toPath() == path)
    }

}