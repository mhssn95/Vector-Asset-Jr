import ext.nextDouble
import model.Svg
import org.junit.Test
import parser.SvgParser
import utils.ElementsUtils.randomPath
import kotlin.random.Random

class PathParser {

    private val svgParser = SvgParser()

    @Test
    fun `parse path`() {
        val path = randomPath(withTransform = false)
        println(path.toXml())
        val a = Svg(Random.nextDouble(255), Random.nextDouble(255), Random.nextDouble(255), Random.nextDouble(255))
        a.addElement(path)
        val svgText = a.toString()
        println(svgText)
        val b = svgParser.parseSvg(svgText)
        assert(b?.elements?.contains(path) ?: false)
    }

    @Test
    fun `parse path without d`() {
        val svgText = """
            <svg width="500" height="200">
                <path />
            </svg>
        """.trimIndent()
        svgParser.parseSvg(svgText)
    }

}