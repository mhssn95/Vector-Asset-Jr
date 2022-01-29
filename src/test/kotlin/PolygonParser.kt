import ext.nextFloat
import ext.nextOddInt
import model.Svg
import model.elements.Path
import org.junit.Test
import parser.SvgParser
import utils.ElementsUtils.randomPolygon
import java.lang.IllegalArgumentException
import kotlin.random.Random

class PolygonParser {

    private val svgParser = SvgParser()

    @Test
    fun `parse polygon`() {
        val polygon = randomPolygon()
        val a = Svg(Random.nextFloat(255), Random.nextFloat(255), Random.nextFloat(255), Random.nextFloat(255))
        a.addElement(polygon)
        val svgText = a.toString()
        val b = svgParser.parseSvg(svgText)
        assert(b.elements.contains(polygon))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse polygon with odd points`() {
        val svgText = """
            <svg>
                <polygon points="${repeat(Random.nextOddInt(1, 255)) { "${Random.nextFloat()}, " }}" />
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse polygon with wrong inputs`() {
        val svgText = """
            <svg>
                <polygon points="i,j,k,l"/>
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

    @Test
    fun `convert polygon to path`() {
        val polygon = randomPolygon()
        val actions = ArrayList<Path.Action>()
        polygon.points.forEachIndexed { index, point ->
            if (index == 0) {
                actions.add(Path.Action.Move(point.x, point.y))
            } else {
                actions.add(Path.Action.LineTo(point.x, point.y))
            }
        }
        actions.add(Path.Action.Close)
        val path = Path(actions, emptySet())
        assert(polygon.toPath() == path)
    }

}