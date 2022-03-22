package model.style.brush.fill

import model.style.brush.Brush

class SolidColor(private val color: String = defaultColor) : Brush() {

    fun toHex(): String {
        if (isValid()) {
            val color = let {
                if (isCssColor()) {
                    cssColors[color]!!
                } else {
                    color
                }
            }.substring(1)
                .toCharArray()
                .map { it.lowercaseChar() }
                .joinToString("")
            return "0x$color"
        }
        return "0x${defaultColor.substring(1)}"
    }

    override fun isValid(): Boolean {
        return (color.isNotBlank() && color.contains(Regex("^#[a-fA-F0-9]{6}\$")) || isCssColor())
    }

    private fun isCssColor(): Boolean {
        return cssColors.containsKey(color)
    }

    companion object {
        const val defaultColor = "#ff000000"
        val cssColors = hashMapOf<String, String>(
            "aliceblue" to "#fff0f8ff",
            "antiquewhite" to "#fffaebd7",
            "aqua" to "#ff00ffff",
            "aquamarine" to "#ff7fffd4",
            "azure" to "#fff0ffff",
            "beige" to "#fff5f5dc",
            "bisque" to "#ffffe4c4",
            "black" to "#ff000000",
            "blanchedalmond" to "#ffffebcd",
            "blue" to "#ff0000ff",
            "blueviolet" to "#ff8a2be2",
            "brown" to "#ffa52a2a",
            "burlywood" to "#ffdeb887",
            "cadetblue" to "#ff5f9ea0",
            "chartreuse" to "#ff7fff00",
            "chocolate" to "#ffd2691e",
            "coral" to "#ffff7f50",
            "cornflowerblue" to "#ff6495ed",
            "cornsilk" to "#fffff8dc",
            "crimson" to "#ffdc143c",
            "cyan" to "#ff00ffff",
            "darkblue" to "#ff00008b",
            "darkcyan" to "#ff008b8b",
            "darkgoldenrod" to "#ffb8860b",
            "darkgray" to "#ffa9a9a9",
            "darkgreen" to "#ff006400",
            "darkgrey" to "#ffa9a9a9",
            "darkkhaki" to "#ffbdb76b",
            "darkmagenta" to "#ff8b008b",
            "darkolivegreen" to "#ff556b2f",
            "darkorange" to "#ffff8c00",
            "darkorchid" to "#ff9932cc",
            "darkred" to "#ff8b0000",
            "darksalmon" to "#ffe9967a",
            "darkseagreen" to "#ff8fbc8f",
            "darkslateblue" to "#ff483d8b",
            "darkslategray" to "#ff2f4f4f",
            "darkslategrey" to "#ff2f4f4f",
            "darkturquoise" to "#ff00ced1",
            "darkviolet" to "#ff9400d3",
            "deeppink" to "#ffff1493",
            "deepskyblue" to "#ff00bfff",
            "dimgray" to "#ff696969",
            "dimgrey" to "#ff696969",
            "dodgerblue" to "#ff1e90ff",
            "firebrick" to "#ffb22222",
            "floralwhite" to "#fffffaf0",
            "forestgreen" to "#ff228b22",
            "fuchsia" to "#ffff00ff",
            "gainsboro" to "#ffdcdcdc",
            "ghostwhite" to "#fff8f8ff",
            "goldenrod" to "#ffdaa520",
            "gold" to "#ffffd700",
            "gray" to "#ff808080",
            "green" to "#ff008000",
            "greenyellow" to "#ffadff2f",
            "grey" to "#ff808080",
            "honeydew" to "#fff0fff0",
            "hotpink" to "#ffff69b4",
            "indianred" to "#ffcd5c5c",
            "indigo" to "#ff4b0082",
            "ivory" to "#fffffff0",
            "khaki" to "#fff0e68c",
            "lavenderblush" to "#fffff0f5",
            "lavender" to "#ffe6e6fa",
            "lawngreen" to "#ff7cfc00",
            "lemonchiffon" to "#fffffacd",
            "lightblue" to "#ffadd8e6",
            "lightcoral" to "#fff08080",
            "lightcyan" to "#ffe0ffff",
            "lightgoldenrodyellow" to "#fffafad2",
            "lightgray" to "#ffd3d3d3",
            "lightgreen" to "#ff90ee90",
            "lightgrey" to "#ffd3d3d3",
            "lightpink" to "#ffffb6c1",
            "lightsalmon" to "#ffffa07a",
            "lightseagreen" to "#ff20b2aa",
            "lightskyblue" to "#ff87cefa",
            "lightslategray" to "#ff778899",
            "lightslategrey" to "#ff778899",
            "lightsteelblue" to "#ffb0c4de",
            "lightyellow" to "#ffffffe0",
            "lime" to "#ff00ff00",
            "limegreen" to "#ff32cd32",
            "linen" to "#fffaf0e6",
            "magenta" to "#ffff00ff",
            "maroon" to "#ff800000",
            "mediumaquamarine" to "#ff66cdaa",
            "mediumblue" to "#ff0000cd",
            "mediumorchid" to "#ffba55d3",
            "mediumpurple" to "#ff9370db",
            "mediumseagreen" to "#ff3cb371",
            "mediumslateblue" to "#ff7b68ee",
            "mediumspringgreen" to "#ff00fa9a",
            "mediumturquoise" to "#ff48d1cc",
            "mediumvioletred" to "#ffc71585",
            "midnightblue" to "#ff191970",
            "mintcream" to "#fff5fffa",
            "mistyrose" to "#ffffe4e1",
            "moccasin" to "#ffffe4b5",
            "navajowhite" to "#ffffdead",
            "navy" to "#ff000080",
            "oldlace" to "#fffdf5e6",
            "olive" to "#ff808000",
            "olivedrab" to "#ff6b8e23",
            "orange" to "#ffffa500",
            "orangered" to "#ffff4500",
            "orchid" to "#ffda70d6",
            "palegoldenrod" to "#ffeee8aa",
            "palegreen" to "#ff98fb98",
            "paleturquoise" to "#ffafeeee",
            "palevioletred" to "#ffdb7093",
            "papayawhip" to "#ffffefd5",
            "peachpuff" to "#ffffdab9",
            "peru" to "#ffcd853f",
            "pink" to "#ffffc0cb",
            "plum" to "#ffdda0dd",
            "powderblue" to "#ffb0e0e6",
            "purple" to "#ff800080",
            "rebeccapurple" to "#ff663399",
            "red" to "#ffff0000",
            "rosybrown" to "#ffbc8f8f",
            "royalblue" to "#ff4169e1",
            "saddlebrown" to "#ff8b4513",
            "salmon" to "#fffa8072",
            "sandybrown" to "#fff4a460",
            "seagreen" to "#ff2e8b57",
            "seashell" to "#fffff5ee",
            "sienna" to "#ffa0522d",
            "silver" to "#ffc0c0c0",
            "skyblue" to "#ff87ceeb",
            "slateblue" to "#ff6a5acd",
            "slategray" to "#ff708090",
            "slategrey" to "#ff708090",
            "snow" to "#fffffafa",
            "springgreen" to "#ff00ff7f",
            "steelblue" to "#ff4682b4",
            "tan" to "#ffd2b48c",
            "teal" to "#ff008080",
            "thistle" to "#ffd8bfd8",
            "tomato" to "#ffff6347",
            "turquoise" to "#ff40e0d0",
            "violet" to "#ffee82ee",
            "wheat" to "#fff5deb3",
            "white" to "#ffffffff",
            "whitesmoke" to "#fff5f5f5",
            "yellow" to "#ffffff00",
            "yellowgreen" to "#ff9acd32",
            "none" to "#00000000",
        )

    }
}