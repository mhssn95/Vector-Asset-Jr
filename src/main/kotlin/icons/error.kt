package icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val error: ImageVector
  get() {
    if (_error != null) {
       return _error!!
    }
    _error = ImageVector.Builder("error", 24.0.dp, 24.0.dp, 24.0f, 24.0f).path(fill =
        SolidColor(Color(0xff000000))){
    	moveTo(12.0f, 2.0f)
    	curveTo(6.48f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
    	reflectiveCurveToRelative(4.48f, 10.0f, 10.0f, 10.0f)
    	reflectiveCurveToRelative(10.0f, -4.48f, 10.0f, -10.0f)
    	reflectiveCurveTo(17.52f, 2.0f, 12.0f, 2.0f)
    	close()
    	moveToRelative(1.0f, 15.0f)
    	horizontalLineToRelative(-2.0f)
    	verticalLineToRelative(-2.0f)
    	horizontalLineToRelative(2.0f)
    	verticalLineToRelative(2.0f)
    	close()
    	moveToRelative(0.0f, -4.0f)
    	horizontalLineToRelative(-2.0f)
    	verticalLineTo(7.0f)
    	horizontalLineToRelative(2.0f)
    	verticalLineToRelative(6.0f)
    	close()
    }.build()
    return _error!!
  }

private var _error: ImageVector? = null
