package model.style.brush.fill

import model.style.brush.Brush
import model.style.brush.Stop

data class LinearGradient(
    val stops: List<Stop>,
    val x1: Float,
    val y1: Float,
    val x2: Float,
    val y2: Float
) : Brush() {
    override fun isValid(): Boolean {
        TODO("Not yet implemented")
    }
}