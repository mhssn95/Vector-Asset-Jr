import ext.nextFloat
import model.Svg
import model.elements.Path
import model.style.Style
import org.junit.Test
import parser.SvgParser
import utils.ElementsUtils.randomCircle
import java.lang.IllegalArgumentException
import kotlin.random.Random

class CircleParser {

    private val svgParser = SvgParser()

    @Test
    fun `parse circle`() {
        val circle = randomCircle()
        val a = Svg(Random.nextFloat(255), Random.nextFloat(255), Random.nextFloat(255), Random.nextFloat(255))
        a.addElement(circle)
        val svgText = a.toString()
        val b = svgParser.parseSvg(svgText)
        assert(b.elements.contains(circle))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse circle without cx`() {
        val svgText = """
            <svg>
                <circle cy="${Random.nextFloat(512)}" r="${Random.nextFloat(512)}"/>
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse circle without cy`() {
        val svgText = """
            <svg>
                <circle cx="${Random.nextFloat(512)}" r="${Random.nextFloat(512)}"/>
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse circle without radius`() {
        val svgText = """
            <svg>
                <circle cx="${Random.nextFloat(512)}" cy="${Random.nextFloat(512)}"/>
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test
    fun `convert circle to path`() {
        val circle = randomCircle()
        val path = Path(
            listOf(
                Path.Action.Move(circle.cx, circle.cy),
                Path.Action.Move(-circle.radius, 0f, true),
                Path.Action.Arc(circle.radius, circle.radius, 0f, 1, 1, circle.radius * 2, 0f),
                Path.Action.Arc(circle.radius, circle.radius, 0f, 1, 1, -circle.radius * 2, 0f)
            )
        )
        assert(circle.toPath() == path)
    }

}