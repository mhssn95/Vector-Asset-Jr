import ext.nextFloat
import model.Svg
import model.elements.Path
import org.junit.Test
import parser.SvgParser
import utils.ElementsUtils.randomEllipse
import java.lang.IllegalArgumentException
import kotlin.random.Random

class EllipseParser {

    private val svgParser = SvgParser()

    @Test
    fun `parse ellipse`() {
        val ellipse = randomEllipse()
        val a = Svg(Random.nextFloat(255), Random.nextFloat(255), Random.nextFloat(255), Random.nextFloat(255))
        a.addElement(ellipse)
        val svgText = a.toString()
        val b = svgParser.parseSvg(svgText)
        assert(b.elements.contains(ellipse))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse ellipse without cx`() {
        val svgText = """
            <svg>
                <ellipse cy="${Random.nextFloat(512)}" rx="${Random.nextFloat(512)}" ry="${Random.nextFloat(512)}"/>
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse ellipse without cy`() {
        val svgText = """
            <svg>
                <ellipse cx="${Random.nextFloat(512)}" rx="${Random.nextFloat(512)}" ry="${Random.nextFloat(512)}"/>
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse ellipse without rx`() {
        val svgText = """
            <svg>
                <ellipse cx="${Random.nextFloat(512)}" cy="${Random.nextFloat(512)}" ry="${Random.nextFloat(512)}"/>
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse ellipse without ry`() {
        val svgText = """
            <svg>
                <ellipse cx="${Random.nextFloat(512)}" cy="${Random.nextFloat(512)}" rx="${Random.nextFloat(512)}"/>
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test
    fun `convert ellipse to path`() {
        val ellipse = randomEllipse()
        val path = Path(
            listOf(
                Path.Action.Move(ellipse.cx - ellipse.rx, ellipse.cy),
                Path.Action.Arc(ellipse.rx, ellipse.ry, 0f, 1, 0, ellipse.rx * 2, 0f, true),
                Path.Action.Arc(ellipse.rx, ellipse.ry, 0f, 1, 0, -ellipse.rx*2, 0f, true),
                Path.Action.Close
            ),
            emptySet()
        )
        assert(ellipse.toPath() == path)
    }

}