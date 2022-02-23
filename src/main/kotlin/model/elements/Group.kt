package model.elements

import model.PathConverter
import model.SvgElement
import model.Transform
import model.style.Style

class Group(override var style: Style = Style(), override var transform: List<Transform>) :
    SvgElement(style, transform) {

    companion object {
        const val NodeName = "g"
    }

    private val _elements: MutableList<SvgElement> = ArrayList()
    val elements: List<SvgElement> = _elements

    fun addElement(element: SvgElement) {
        element.transform = transform + element.transform
        element.style = element.style.fillStyle(style)
        _elements.add(element)
    }

    override fun toXml(): String {
        return "<$NodeName>\n${
            elements.joinToString("") {
                "${
                    if (it is PathConverter) {
                        it.toPath().toXml()
                    } else {
                        it.toXml()
                    }
                }\n"
            }
        }</$NodeName>"
    }
}