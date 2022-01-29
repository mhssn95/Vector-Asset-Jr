package model.style.brush

import model.style.brush.fill.SolidColor

data class Stop(val offset: Int, val color: SolidColor, val opacity: Float)