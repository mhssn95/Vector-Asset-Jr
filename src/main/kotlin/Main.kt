import model.PathConverter
import parser.SvgParser
import java.io.File

fun main() {
    val svgFile = File("svgs/home.svg")
    val svgParser = SvgParser()
    val svg = svgParser.parseSvg(svgFile)
    svg.elements.forEach {
        if (it is PathConverter) {
            println(it.toPath().toXml())
        } else {
            println(it.toXml())
        }
    }
    val generator = CodeGenerator()
    generator.generateVector("vector", svg)
}