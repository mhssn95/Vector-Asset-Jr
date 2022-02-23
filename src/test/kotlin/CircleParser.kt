import ext.nextDouble
import model.Svg
import model.elements.Path
import org.junit.Test
import parser.SvgParser
import utils.ElementsUtils.randomCircle
import java.lang.IllegalArgumentException
import kotlin.random.Random

class CircleParser {

    private val svgParser = SvgParser()

    @Test
    fun `parse circle`() {
        val circle = randomCircle(withTransform = false)
        val a = Svg(Random.nextDouble(255), Random.nextDouble(255), Random.nextDouble(255), Random.nextDouble(255))
        a.addElement(circle)
        val svgText = a.toString()
        val b = svgParser.parseSvg(svgText)
        assert(b?.elements?.contains(circle)?:false)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse circle without cx`() {
        val svgText = """
            <svg>
                <circle cy="${Random.nextDouble(512)}" r="${Random.nextDouble(512)}"/>
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse circle without cy`() {
        val svgText = """
            <svg>
                <circle cx="${Random.nextDouble(512)}" r="${Random.nextDouble(512)}"/>
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse circle without radius`() {
        val svgText = """
            <svg>
                <circle cx="${Random.nextDouble(512)}" cy="${Random.nextDouble(512)}"/>
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test
    fun `convert circle to path`() {
        val circle = randomCircle(withTransform = false)
        val path = Path(
            listOf(
                Path.Action.Move(circle.cx, circle.cy),
                Path.Action.Move(-circle.radius, 0.0, true),
                Path.Action.Arc(circle.radius, circle.radius, 0.0, 1, 1, circle.radius * 2, 0.0),
                Path.Action.Arc(circle.radius, circle.radius, 0.0, 1, 1, -circle.radius * 2, 0.0)
            )
        )
        assert(circle.toPath() == path)
    }

}