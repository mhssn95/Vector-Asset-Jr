package model

import model.style.Style

abstract class SvgElement(open var style: Style, open var transform: List<Transform>) {
    abstract fun toXml(): String
}