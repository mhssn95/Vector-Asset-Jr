package model.style.brush.fill

import model.style.brush.Brush
import model.style.brush.Stop

data class RadialGradient(
    val stops: List<Stop>,
    val cx: Float,
    val cy: Float,
    val radius: Float,
    val fx: Float,
    val fy: Float
) : Brush() {
    override fun isValid(): Boolean {
        TODO("Not yet implemented")
    }
}