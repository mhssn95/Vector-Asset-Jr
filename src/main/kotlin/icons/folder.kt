package icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val folder: ImageVector
    get() {
        if (_folder != null) {
            return _folder!!
        }
        _folder = ImageVector.Builder("folder", 24.0.dp, 24.0.dp, 24.0f, 24.0f).path(
            fill =
            SolidColor(Color(0x00000000))
        ) {
            moveTo(0.0f, 0.0f)
            horizontalLineToRelative(24.0f)
            verticalLineToRelative(24.0f)
            horizontalLineTo(0.0f)
            close()
        }.path(fill = SolidColor(Color(0xff000000))) {
            moveTo(10.0f, 4.0f)
            horizontalLineTo(4.0f)
            curveToRelative(-1.1f, 0.0f, -1.99f, 0.9f, -1.99f, 2.0f)
            lineTo(2.0f, 18.0f)
            curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
            horizontalLineToRelative(16.0f)
            curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
            verticalLineTo(8.0f)
            curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
            horizontalLineToRelative(-8.0f)
            lineToRelative(-2.0f, -2.0f)
            close()
        }.build()
        return _folder!!
    }

private var _folder: ImageVector? = null
