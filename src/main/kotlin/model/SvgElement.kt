package model

import model.style.Style

abstract class SvgElement(open var style: Style) {
    abstract fun toXml(): String
}