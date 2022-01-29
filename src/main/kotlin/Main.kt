import parser.SvgParser
import java.io.File

fun main() {
    val svgFile = File("svgs/home.svg")
    val svgParser = SvgParser()
    val svg = svgParser.parseSvg(svgFile)
    svg?.let {
        val generator = CodeGenerator()
        generator.generateVector("vector", it)
    }
}