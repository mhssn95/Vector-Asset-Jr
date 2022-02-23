package model

class Svg(val width: Double, val height: Double, val viewportWidth: Double, val viewportHeight: Double) {
    private val _elements: MutableList<SvgElement> = ArrayList()
    val elements: List<SvgElement> = _elements

    fun addElement(element: SvgElement) {
        _elements.add(element)
    }

    override fun toString(): String {
        return "<svg width=\"$width\" height=\"$height\" viewBox=\"0 0 $viewportWidth $viewportHeight\" >\n${elements.joinToString { "\t${it.toXml()}\n" }}</svg>"
    }
}