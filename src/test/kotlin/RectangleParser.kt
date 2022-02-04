import ext.nextFloat
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
        val a = Svg(Random.nextFloat(255), Random.nextFloat(255), Random.nextFloat(255), Random.nextFloat(255))
        val rect = randomRect(radiusEnabled = false)
        a.addElement(rect)
        val svgText = a.toString()
        val b = svgParser.parseSvg(svgText)
        assert(b?.elements?.contains(rect) ?: false)
    }

    @Test
    fun `parse rectangle with both radius`() {
        val a = Svg(Random.nextFloat(255), Random.nextFloat(255), Random.nextFloat(255), Random.nextFloat(255))
        val rect = randomRect(radiusEnabled = true)
        a.addElement(rect)
        val svgText = a.toString()
        val b = svgParser.parseSvg(svgText)
        assert(b?.elements?.contains(rect) ?: false)
    }

    @Test
    fun `parse rectangle with rx radius`() {
        val a = Svg(Random.nextFloat(255), Random.nextFloat(255), Random.nextFloat(255), Random.nextFloat(255))
        val rect = randomRect(ry = null, radiusEnabled = true)
        a.addElement(rect)
        val svgText = a.toString()
        val b = svgParser.parseSvg(svgText)
        assert(b?.elements?.contains(rect) ?: false)
    }

    @Test
    fun `parse rectangle with ry radius`() {
        val a = Svg(Random.nextFloat(255), Random.nextFloat(255), Random.nextFloat(255), Random.nextFloat(255))
        val rect = randomRect(rx = null, radiusEnabled = true)
        a.addElement(rect)
        val svgText = a.toString()
        val b = svgParser.parseSvg(svgText)
        assert(b?.elements?.contains(rect) ?: false)
    }

    @Test
    fun `parse rectangle with x only`() {
        val a = Svg(Random.nextFloat(255), Random.nextFloat(255), Random.nextFloat(255), Random.nextFloat(255))
        val rect = randomRect(y = null, xyEnabled = true)
        a.addElement(rect)
        val svgText = a.toString()
        val b = svgParser.parseSvg(svgText)
        assert(b?.elements?.contains(rect) ?: false)
    }

    @Test
    fun `parse rectangle with y only`() {
        val a = Svg(Random.nextFloat(255), Random.nextFloat(255), Random.nextFloat(255), Random.nextFloat(255))
        val rect = randomRect(x = null, xyEnabled = true)
        a.addElement(rect)
        val svgText = a.toString()
        val b = svgParser.parseSvg(svgText)
        assert(b?.elements?.contains(rect) ?: false)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse rectangle without width`() {
        val svg = """
            <svg>
                <rect x="${Random.nextFloat(512)}" y="${Random.nextFloat(512)}" height="${Random.nextFloat(512)}" />
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svg)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse rectangle without height`() {
        val svg = """
            <svg>
                <rect x="${Random.nextFloat(512)}" y="${Random.nextFloat(512)}" width="${Random.nextFloat(512)}" />
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svg)
    }

    @Test
    fun `convert rect to path without radius`() {
        val rect = randomRect(radiusEnabled = false)
        val x = rect.x ?: 0f
        val y = rect.y ?: 0f
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
        val rect = randomRect(radiusEnabled = true)
        val x = rect.x ?: 0f
        val y = rect.y ?: 0f
        val rx = rect.rx ?: 0f
        val ry = rect.ry ?: 0f
        val path = Path(
            listOf(
                Path.Action.Move(x + rx, y),
                Path.Action.LineTo(x + rect.width - rx, y),
                Path.Action.Arc(rx, ry, 0f, 0, 1, x + rect.width, y + ry),
                Path.Action.LineTo(x + rect.width, y + rect.height - ry),
                Path.Action.Arc(rx, ry, 0f, 0, 1, x + rect.width - rx, y + rect.height),
                Path.Action.LineTo(x + rx, y + rect.height),
                Path.Action.Arc(rx, ry, 0f, 0, 1, x, y + rect.height - ry),
                Path.Action.LineTo(x, y + ry),
                Path.Action.Arc(rx, ry, 0f, 0, 1, x + rx, y),
                Path.Action.Close
            )
        )
        assert(rect.toPath() == path)
    }

}