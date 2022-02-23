import ext.nextDouble
import model.Svg
import org.junit.Test
import parser.SvgParser
import java.lang.IllegalArgumentException
import kotlin.random.Random

class ParseSvgAttrs {

    private val svgParser = SvgParser()

    @Test
    fun `parse svg with valid width and height and viewBox`() {
        val width = Random.nextDouble(255)
        val height = Random.nextDouble(255)
        val viewportWidth = Random.nextDouble(255)
        val viewportHeight = Random.nextDouble(255)
        val a = Svg(width, height, viewportWidth, viewportHeight)
        val svg = svgParser.parseSvg(a.toString())
        assert(svg?.width == width && svg.height == height && svg.viewportWidth == viewportWidth && svg.viewportHeight == viewportHeight)
    }

    @Test
    fun `parse svg without height`() {
        val width = Random.nextDouble(255)
        val a = "<svg width=\"$width\"></svg>"
        val svg = svgParser.parseSvg(a)
        assert(svg?.width == width && svg.height == width)
    }

    @Test
    fun `parse svg without width`() {
        val height = Random.nextDouble(255)
        val a = "<svg height=\"$height\"></svg>"
        val svg = svgParser.parseSvg(a)
        assert(svg?.width == height && svg.height == height)
    }

    @Test
    fun `parse svg without viewport`() {
        val width = Random.nextDouble(255)
        val height = Random.nextDouble(255)
        val a = "<svg width=\"$width\" height=\"$height\"></svg>"
        val svg = svgParser.parseSvg(a)
        assert(svg?.viewportWidth == width && svg.viewportHeight == height)
    }

    @Test
    fun `calc svg viewport width and height`() {
        val l = Random.nextDouble(255)
        val t = Random.nextDouble(255)
        val r = l + Random.nextDouble(255) //just to make sure that right is bigger than left
        val b = t + Random.nextDouble(255) //just to make sure that bottom is bigger than top
        val a = "<svg viewBox=\"$l $t $r $b\"></svg>"
        val svg = svgParser.parseSvg(a)
        assert(svg?.viewportWidth == r - l && svg.viewportHeight == b - t)
    }

    @Test
    fun `parse svg without width and height`() {
        val l = Random.nextDouble(255)
        val t = Random.nextDouble(255)
        val r = l + Random.nextDouble(255) //just to make sure that right is bigger than left
        val b = t + Random.nextDouble(255) //just to make sure that bottom is bigger than top
        val a = "<svg viewBox=\"$l $t $r $b\"></svg>"
        val svg = svgParser.parseSvg(a)
        assert(svg?.width == r - l && svg.height == b - t)
    }

    @Test
    fun `calc svg viewport with left bigger that right and top bigger than bottom`() {
        val r = Random.nextDouble(255)
        val l = r + Random.nextDouble(255)
        val b = Random.nextDouble(255)
        val t = b + Random.nextDouble(255)
        val a = "<svg viewBox=\"$l $t $r $b\"></svg>"
        val svg = svgParser.parseSvg(a)
        assert(svg?.viewportWidth == 0.0 && svg.viewportHeight == 0.0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse svg without width and height and viewBox`() {
        val a = "<svg></svg>"
        svgParser.parseSvg(a)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse svg with not valid viewBox`() {
        val a = "<svg viewBox=\"4 5\"></svg>"
        svgParser.parseSvg(a)
    }

    @Test
    fun `parse svg with not valid viewBox and with width and height`() {
        val width = Random.nextDouble(255)
        val height = Random.nextDouble(255)
        val a = "<svg width=\"${width}\" height=\"${height}\" viewBox=\"4 5\"></svg>"
        val svg = svgParser.parseSvg(a)
        assert(svg?.viewportWidth == width && svg.viewportHeight == height)
    }
}