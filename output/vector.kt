import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val vector: ImageVector
  get() {
    if (_vector != null) {
       return _vector!!
    }
    _vector = ImageVector.Builder("vector", 512.0.dp, 512.0.dp, 512.0f, 512.0f).group {
    	path(0xffff0000){
    		moveTo(0.0f, 0.0f)
    		horizontalLineToRelative(3.0f)
    		verticalLineToRelative(5.0f)
    		horizontalLineToRelative(-3.0f)
    		close()
    	}
    	group {
    		path(0xffffff00){
    			moveTo(4.0f, 4.0f)
    			moveToRelative(-9.0f, 0.0f)
    			arcTo(9.0f, 9.0f, 0.0f, false, false, 18.0f, 0.0f)
    			arcTo(9.0f, 9.0f, 0.0f, false, false, -18.0f, 0.0f)
    		}
    		group {
    			path(0xff000000){
    				moveTo(4.0f, 4.0f)
    				moveToRelative(-9.0f, 0.0f)
    				arcTo(9.0f, 9.0f, 0.0f, false, false, 18.0f, 0.0f)
    				arcTo(9.0f, 9.0f, 0.0f, false, false, -18.0f, 0.0f)
    			}
    		}
    	}
    }.path(0xffffffff){
    	moveTo(0.0f, 0.0f)
    	horizontalLineToRelative(512.0f)
    	verticalLineToRelative(512.0f)
    	horizontalLineToRelative(-512.0f)
    	close()
    }.path(0xff000000){
    	moveTo(57.7158f, 76.51213f)
    	quadToRelative(165.62085f, 219.14528f, 106.67051f, 42.342876f)
    	quadTo(107.63289f, 203.31972f, 149.52316f, 197.85358f)
    	reflectiveCurveTo(173.6087f, 197.57343f, 179.70593f, 0.95355f)
    	reflectiveCurveToRelative(127.867165f, 74.21139f, 186.72166f, 0.6744f)
    	curveToRelative(129.6353f, 167.18718f, 79.78548f, 199.44211f, 162.24309f, 46.55346f)
    	close()
    	close()
    	close()
    	curveTo(181.40878f, 101.59225f, 3.7118688f, 25.236832f, 201.48752f, 138.15631f)
    	close()
    	curveTo(111.25496f, 240.93155f, 138.98938f, 233.75223f, 151.48624f, 254.22845f)
    	quadTo(145.30164f, 240.17027f, 42.37887f, 97.58916f)
    	curveTo(164.85913f, 132.57724f, 33.251255f, 245.83452f, 57.99197f, 0.69036216f)
    	curveTo(46.26586f, 235.114f, 66.03228f, 181.58492f, 237.37462f, 222.2973f)
    	close()
    	reflectiveCurveToRelative(93.587715f, 30.383732f, 185.94128f, 0.30495f)
    	curveTo(148.65404f, 47.811314f, 130.6234f, 48.851593f, 137.91005f, 100.43325f)
    	quadToRelative(92.476036f, 72.87901f, 34.000896f, 8.565682f)
    	reflectiveCurveToRelative(9.899746f, 190.11261f, 208.16447f, 0.81334f)
    	close()
    	curveTo(108.30559f, 75.256485f, 69.97669f, 106.69974f, 223.85278f, 169.66711f)
    	curveToRelative(121.11879f, 212.5907f, 191.55981f, 225.74901f, 43.18684f, 145.10445f)
    	reflectiveCurveToRelative(116.001495f, 215.0765f, 157.85672f, 0.02328f)
    	close()
    	close()
    	close()
    	close()
    	curveToRelative(244.49013f, 85.93547f, 160.14162f, 234.61464f, 168.57516f, 27.971075f)
    	close()
    	curveTo(234.751f, 84.76842f, 201.68626f, 162.87544f, 98.82631f, 109.148766f)
    	close()
    	curveTo(202.6153f, 169.97238f, 160.79327f, 191.51306f, 12.394586f, 193.72606f)
    	curveTo(2.2337027f, 70.85995f, 197.2529f, 240.96149f, 217.45667f, 5.918547f)
    	close()
    	close()
    	close()
    	close()
    	curveToRelative(247.88087f, 115.93816f, 96.159065f, 180.88036f, 209.61848f, 108.031364f)
    	curveToRelative(39.694084f, 75.69594f, 121.509026f, 85.55527f, 135.33273f, 97.74597f)
    	curveToRelative(31.814112f, 236.07968f, 230.16838f, 34.80402f, 197.77383f, 145.5039f)
    	quadToRelative(210.14693f, 228.85828f, 148.6412f, 105.4698f)
    	close()
    	quadToRelative(143.53827f, 248.10133f, 83.78447f, 231.59494f)
    	close()
    	close()
    	reflectiveCurveTo(201.5536f, 83.49213f, 134.70557f, 0.620003f)
    	quadToRelative(87.92848f, 9.207194f, 170.61241f, 27.012918f)
    	quadToRelative(10.109783f, 87.55749f, 232.9055f, 156.32622f)
    	quadToRelative(127.657585f, 172.15096f, 0.014484823f, 216.14352f)
    	close()
    	quadTo(25.463558f, 249.3229f, 55.555893f, 173.33394f)
    }.build()
    return _vector!!
  }

private var _vector: ImageVector? = null
