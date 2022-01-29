import org.w3c.dom.Node

fun Node.getAttr(name: String): String? {
    return this.attributes.getNamedItem(name)?.nodeValue
}