package model

class Svg(val width: Float, val height: Float, val viewportWidth: Float, val viewportHeight: Float) {
    private val _elements: MutableList<SvgElement> = ArrayList()
    val elements: List<SvgElement> = _elements

    fun addElement(element: SvgElement) {
        _elements.add(element)
    }

    override fun toString(): String {
        return "<svg>\n${elements.joinToString { "\t${it.toXml()}\n" }}</svg>"
    }
}