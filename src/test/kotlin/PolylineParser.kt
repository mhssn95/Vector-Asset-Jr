import ext.nextFloat
import ext.nextOddInt
import model.Svg
import model.elements.Path
import org.junit.Test
import parser.SvgParser
import utils.ElementsUtils.randomPolyline
import java.lang.IllegalArgumentException
import kotlin.random.Random

class PolylineParser {

    private val svgParser = SvgParser()

    @Test
    fun `parse polyline`() {
        val polyline = randomPolyline()
        val a = Svg(Random.nextFloat(255), Random.nextFloat(255), Random.nextFloat(255), Random.nextFloat(255))
        a.addElement(polyline)
        val svgText = a.toString()
        val b = svgParser.parseSvg(svgText)
        assert(b.elements.contains(polyline))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse polyline with odd points`() {
        val svgText = """
            <svg>
                <polyline points="${repeat(Random.nextOddInt(1, 255)) { "${Random.nextFloat()}, " }}" />
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse polyline with wrong inputs`() {
        val svgText = """
            <svg>
                <polyline points="i,j,k,l"/>
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test
    fun `convert polyline to path`() {
        val polyline = randomPolyline()
        val actions = ArrayList<Path.Action>()
        polyline.points.forEachIndexed { index, point ->
            if (index == 0) {
                actions.add(Path.Action.Move(point.x, point.y))
            } else {
                actions.add(Path.Action.LineTo(point.x, point.y))
            }
        }
        actions.add(Path.Action.Close)
        val path = Path(actions, emptySet())
        assert(polyline.toPath() == path)
    }

}