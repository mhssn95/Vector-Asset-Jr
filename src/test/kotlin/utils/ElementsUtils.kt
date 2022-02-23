@file:Suppress("NAME_SHADOWING")

package utils

import ext.nextDouble
import model.Point
import model.Transform
import model.elements.*
import kotlin.random.Random

object ElementsUtils {

    fun randomRect(
        x: Double? = Random.nextDouble(512),
        y: Double? = Random.nextDouble(512),
        rx: Double? = Random.nextDouble(512),
        ry: Double? = Random.nextDouble(512),
        width: Double = Random.nextDouble(512),
        height: Double = Random.nextDouble(512),
        xyEnabled: Boolean = Random.nextBoolean(),
        radiusEnabled: Boolean = Random.nextBoolean(),
        withTransform: Boolean = Random.nextBoolean()
    ): Rectangle {
        val x = if (xyEnabled) {
            x
        } else {
            null
        }
        val y = if (xyEnabled) {
            y
        } else {
            null
        }
        val rx = if (radiusEnabled) {
            rx
        } else {
            null
        }
        val ry = if (radiusEnabled) {
            ry
        } else {
            null
        }
        return Rectangle(x, y, rx, ry, width, height, transform = if (withTransform) randomTransform() else emptyList())
    }

    fun randomLine(
        x1: Double = Random.nextDouble(512),
        y1: Double = Random.nextDouble(512),
        x2: Double = Random.nextDouble(512),
        y2: Double = Random.nextDouble(512),
        withTransform: Boolean = Random.nextBoolean()
    ): Line {
        return Line(x1, y1, x2, y2, transform = if (withTransform) randomTransform() else emptyList())
    }

    fun randomCircle(
        cx: Double = Random.nextDouble(512),
        cy: Double = Random.nextDouble(512),
        radius: Double = Random.nextDouble(512),
        withTransform: Boolean = Random.nextBoolean()
    ): Circle {
        return Circle(cx, cy, radius, transform = if (withTransform) randomTransform() else emptyList())
    }

    fun randomEllipse(
        cx: Double = Random.nextDouble(512),
        cy: Double = Random.nextDouble(512),
        rx: Double = Random.nextDouble(512),
        ry: Double = Random.nextDouble(512),
        withTransform: Boolean = Random.nextBoolean()
    ): Ellipse {
        return Ellipse(cx, cy, rx, ry, transform = if (withTransform) randomTransform() else emptyList())
    }

    fun randomPolygon(
        pointsPairLength: Int = Random.nextInt(1, 100),
        withTransform: Boolean = Random.nextBoolean()
    ): Polygon {
        val points = ArrayList<Point>()
        repeat(pointsPairLength) {
            points.add(Point(Random.nextDouble(255), Random.nextDouble(255)))
        }
        return Polygon(points, transform = if (withTransform) randomTransform() else emptyList())
    }

    fun randomPolyline(
        pointsPairLength: Int = Random.nextInt(1, 100),
        withTransform: Boolean = Random.nextBoolean()
    ): Polygon {
        val points = ArrayList<Point>()
        repeat(pointsPairLength) {
            points.add(Point(Random.nextDouble(255), Random.nextDouble(255)))
        }
        return Polygon(points, transform = if (withTransform) randomTransform() else emptyList())
    }

    fun randomPath(
        numOfActions: Int = Random.nextInt(1, 100),
        withMove: Boolean = Random.nextBoolean(),
        withHorizontalLine: Boolean = Random.nextBoolean(),
        withVerticalLine: Boolean = Random.nextBoolean(),
        withLineTo: Boolean = Random.nextBoolean(),
        withQuadratic: Boolean = Random.nextBoolean(),
        withCurve: Boolean = Random.nextBoolean(),
        withArc: Boolean = Random.nextBoolean(),
        withSmooth: Boolean = Random.nextBoolean(),
        withSmoothQuadratic: Boolean = Random.nextBoolean(),
        withClose: Boolean = Random.nextBoolean(),
        withTransform: Boolean = Random.nextBoolean()
    ): Path {
        val enabledActions = ArrayList<Int>()
        if (withMove) {
            enabledActions.add(0)
        }
        if (withHorizontalLine) {
            enabledActions.add(1)
        }
        if (withVerticalLine) {
            enabledActions.add(2)
        }
        if (withLineTo) {
            enabledActions.add(3)
        }
        if (withQuadratic) {
            enabledActions.add(4)
        }
        if (withCurve) {
            enabledActions.add(5)
        }
        if (withArc) {
            enabledActions.add(6)
        }
        if (withSmooth) {
            enabledActions.add(7)
        }
        if (withSmoothQuadratic) {
            enabledActions.add(8)
        }
        if (withClose) {
            enabledActions.add(9)
        }
        val actions = ArrayList<Path.Action>()
        actions.add(Path.Action.Move(Random.nextDouble(255), Random.nextDouble(255)))
        repeat(numOfActions) {
            val action = Random.nextInt(enabledActions.size)
            actions.add(
                when (enabledActions[action]) {
                    0 -> {
                        Path.Action.Move(Random.nextDouble(255), Random.nextDouble(255), Random.nextBoolean())
                    }
                    1 -> {
                        Path.Action.HorizontalLine(Random.nextDouble(255), Random.nextBoolean())
                    }
                    2 -> {
                        Path.Action.VerticalLine(Random.nextDouble(255), Random.nextBoolean())
                    }
                    3 -> {
                        Path.Action.LineTo(Random.nextDouble(255), Random.nextDouble(255), Random.nextBoolean())
                    }
                    4 -> {
                        Path.Action.Quadratic(
                            Random.nextDouble(255),
                            Random.nextDouble(255),
                            Random.nextDouble(255),
                            Random.nextDouble(255),
                            Random.nextBoolean()
                        )
                    }
                    5 -> {
                        Path.Action.Curve(
                            Random.nextDouble(255),
                            Random.nextDouble(255),
                            Random.nextDouble(255),
                            Random.nextDouble(255),
                            Random.nextDouble(255),
                            Random.nextDouble(255),
                            Random.nextBoolean()
                        )
                    }
                    6 -> {
                        Path.Action.Arc(
                            Random.nextDouble(255),
                            Random.nextDouble(255),
                            Random.nextDouble(360),
                            Random.nextInt(0, 1),
                            Random.nextInt(0, 1),
                            Random.nextDouble(255),
                            Random.nextDouble(255),
                            Random.nextBoolean()
                        )
                    }
                    7 -> {
                        Path.Action.Smooth(
                            Random.nextDouble(255),
                            Random.nextDouble(255),
                            Random.nextDouble(255),
                            Random.nextDouble(255),
                            Random.nextBoolean()
                        )
                    }
                    8 -> {
                        Path.Action.SmoothQuadratic(
                            Random.nextDouble(255),
                            Random.nextDouble(255),
                            Random.nextBoolean()
                        )
                    }
                    9 -> {
                        Path.Action.Close
                    }
                    else -> throw IllegalArgumentException()
                }
            )
        }
        return Path(actions, transform = if (withTransform) randomTransform() else emptyList())
    }

    private fun randomTransform(
        numOfActions: Int = Random.nextInt(1, 100),
        withMatrix: Boolean = Random.nextBoolean(),
        withTranslate: Boolean = Random.nextBoolean(),
        withScale: Boolean = Random.nextBoolean(),
        withRotation: Boolean = Random.nextBoolean(),
        withSkewX: Boolean = Random.nextBoolean(),
        withSkewY: Boolean = Random.nextBoolean(),
    ): List<Transform> {
        val enabledTransform = ArrayList<Int>()
        if (withMatrix) {
            enabledTransform.add(0)
        }
        if (withTranslate) {
            enabledTransform.add(1)
        }
        if (withScale) {
            enabledTransform.add(2)
        }
        if (withRotation) {
            enabledTransform.add(3)
        }
        if (withSkewX) {
            enabledTransform.add(4)
        }
        if (withSkewY) {
            enabledTransform.add(5)
        }
        val transformList = ArrayList<Transform>()
        repeat(numOfActions) {
            val transform = Random.nextInt(enabledTransform.size)
            transformList.add(
                when (enabledTransform[transform]) {
                    0 -> {
                        Transform.Matrix(
                            Random.nextDouble(255),
                            Random.nextDouble(255),
                            Random.nextDouble(255),
                            Random.nextDouble(255),
                            Random.nextDouble(255),
                            Random.nextDouble(255),
                        )
                    }
                    1 -> {
                        Transform.Translate(
                            Random.nextDouble(255),
                            Random.nextDouble(255)
                        )
                    }
                    2 -> {
                        Transform.Scale(
                            Random.nextDouble(255),
                            Random.nextDouble(255)
                        )
                    }
                    3 -> {
                        Transform.Rotate(
                            Random.nextDouble(255),
                            Random.nextDouble(255),
                            Random.nextDouble(255)
                        )
                    }
                    4 -> {
                        Transform.SkewX(
                            Random.nextDouble(255),
                        )
                    }
                    5 -> {
                        Transform.SkewY(
                            Random.nextDouble(255)
                        )
                    }
                    else -> throw IllegalArgumentException()
                }
            )
        }
        return transformList
    }

}