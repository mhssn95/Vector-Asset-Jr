package model.style

import model.style.brush.Brush

data class Style(
    val fill: Brush? = null,
    val opacity: Float? = null,
) {

    fun fillStyle(style: Style): Style {
        val fill = if (this.fill != null && this.fill.isValid()) {
            this.fill
        } else {
            style.fill
        }
        val opacity = this.opacity ?: style.opacity
        return this.copy(fill = fill, opacity = opacity)
    }
}