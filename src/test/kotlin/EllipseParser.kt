import ext.nextDouble
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
        val ellipse = randomEllipse(withTransform = false)
        val a = Svg(Random.nextDouble(255), Random.nextDouble(255), Random.nextDouble(255), Random.nextDouble(255))
        a.addElement(ellipse)
        val svgText = a.toString()
        val b = svgParser.parseSvg(svgText)
        assert(b?.elements?.contains(ellipse)?:false)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse ellipse without cx`() {
        val svgText = """
            <svg>
                <ellipse cy="${Random.nextDouble(512)}" rx="${Random.nextDouble(512)}" ry="${Random.nextDouble(512)}"/>
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse ellipse without cy`() {
        val svgText = """
            <svg>
                <ellipse cx="${Random.nextDouble(512)}" rx="${Random.nextDouble(512)}" ry="${Random.nextDouble(512)}"/>
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse ellipse without rx`() {
        val svgText = """
            <svg>
                <ellipse cx="${Random.nextDouble(512)}" cy="${Random.nextDouble(512)}" ry="${Random.nextDouble(512)}"/>
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse ellipse without ry`() {
        val svgText = """
            <svg>
                <ellipse cx="${Random.nextDouble(512)}" cy="${Random.nextDouble(512)}" rx="${Random.nextDouble(512)}"/>
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test
    fun `convert ellipse to path`() {
        val ellipse = randomEllipse(withTransform = false)
        val path = Path(
            listOf(
                Path.Action.Move(ellipse.cx - ellipse.rx, ellipse.cy),
                Path.Action.Arc(ellipse.rx, ellipse.ry, 0.0, 1, 0, ellipse.rx * 2, 0.0, true),
                Path.Action.Arc(ellipse.rx, ellipse.ry, 0.0, 1, 0, -ellipse.rx*2, 0.0, true),
                Path.Action.Close
            )
        )
        assert(ellipse.toPath() == path)
    }

}