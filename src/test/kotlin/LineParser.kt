import ext.nextDouble
import model.Svg
import model.elements.Path
import org.junit.Test
import parser.SvgParser
import utils.ElementsUtils.randomLine
import java.lang.IllegalArgumentException
import kotlin.random.Random

class LineParser {

    private val svgParser = SvgParser()

    @Test
    fun `parse line`() {
        val line = randomLine(withTransform = false)
        val a = Svg(Random.nextDouble(255), Random.nextDouble(255), Random.nextDouble(255), Random.nextDouble(255))
        a.addElement(line)
        val svgText = a.toString()
        val b = svgParser.parseSvg(svgText)
        assert(b?.elements?.contains(line) ?: false)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse line without x1`() {
        val svgText = """
            <svg>
                <line y1="${Random.nextDouble(512)}" x2="${Random.nextDouble(512)}" y2="${Random.nextDouble(512)}"/>
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse line without y1`() {
        val svgText = """
            <svg>
                <line x1="${Random.nextDouble(512)}" x2="${Random.nextDouble(512)}" y2="${Random.nextDouble(512)}"/>
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse line without x2`() {
        val svgText = """
            <svg>
                <line x1="${Random.nextDouble(512)}" y1="${Random.nextDouble(512)}" y2="${Random.nextDouble(512)}"/>
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse line without y2`() {
        val svgText = """
            <svg>
                <line x1="${Random.nextDouble(512)}" y1="${Random.nextDouble(512)}" x2="${Random.nextDouble(512)}"/>
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test
    fun `convert line to path`() {
        val line = randomLine(withTransform = false)
        val path = Path(
            listOf(
                Path.Action.Move(line.x1, line.y1),
                Path.Action.LineTo(line.x2, line.y2)
            )
        )
        assert(line.toPath() == path)
    }

}