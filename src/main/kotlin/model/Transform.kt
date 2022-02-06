package model

sealed class Transform {
    class Matrix(val a: Float, val b: Float, val c: Float, val d: Float, val e: Float, val f: Float) : Transform()
    class Translate(val x: Float, val y: Float) : Transform()
    class Scale(val x: Float, val y: Float) : Transform()
    class Rotate(val a: Float, val x: Float, val y: Float) : Transform()
    class SkewX(val a: Float): Transform()
    class SkewY(val a: Float): Transform()
}
