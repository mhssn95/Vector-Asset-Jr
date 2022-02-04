@file:Suppress("NAME_SHADOWING")

package utils

import ext.nextFloat
import model.Point
import model.elements.*
import kotlin.random.Random

object ElementsUtils {

    fun randomRect(
        x: Float? = Random.nextFloat(512),
        y: Float? = Random.nextFloat(512),
        rx: Float? = Random.nextFloat(512),
        ry: Float? = Random.nextFloat(512),
        width: Float = Random.nextFloat(512),
        height: Float = Random.nextFloat(512),
        xyEnabled: Boolean = Random.nextBoolean(),
        radiusEnabled: Boolean = Random.nextBoolean()
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
        return Rectangle(x, y, rx, ry, width, height)
    }

    fun randomLine(
        x1: Float = Random.nextFloat(512),
        y1: Float = Random.nextFloat(512),
        x2: Float = Random.nextFloat(512),
        y2: Float = Random.nextFloat(512)
    ): Line {
        return Line(x1, y1, x2, y2)
    }

    fun randomCircle(
        cx: Float = Random.nextFloat(512),
        cy: Float = Random.nextFloat(512),
        radius: Float = Random.nextFloat(512)
    ): Circle {
        return Circle(cx, cy, radius)
    }

    fun randomEllipse(
        cx: Float = Random.nextFloat(512),
        cy: Float = Random.nextFloat(512),
        rx: Float = Random.nextFloat(512),
        ry: Float = Random.nextFloat(512)
    ): Ellipse {
        return Ellipse(cx, cy, rx, ry)
    }

    fun randomPolygon(pointsPairLength: Int = Random.nextInt(1, 100)): Polygon {
        val points = ArrayList<Point>()
        repeat(pointsPairLength) {
            points.add(Point(Random.nextFloat(255), Random.nextFloat(255)))
        }
        return Polygon(points)
    }

    fun randomPolyline(pointsPairLength: Int = Random.nextInt(1, 100)): Polygon {
        val points = ArrayList<Point>()
        repeat(pointsPairLength) {
            points.add(Point(Random.nextFloat(255), Random.nextFloat(255)))
        }
        return Polygon(points)
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
        actions.add(Path.Action.Move(Random.nextFloat(255), Random.nextFloat(255)))
        repeat(numOfActions) {
            val action = Random.nextInt(enabledActions.size)
            actions.add(
                when (enabledActions[action]) {
                    0 -> {
                        Path.Action.Move(Random.nextFloat(255), Random.nextFloat(255), Random.nextBoolean())
                    }
                    1 -> {
                        Path.Action.HorizontalLine(Random.nextFloat(255), Random.nextBoolean())
                    }
                    2 -> {
                        Path.Action.VerticalLine(Random.nextFloat(255), Random.nextBoolean())
                    }
                    3 -> {
                        Path.Action.LineTo(Random.nextFloat(255), Random.nextFloat(255), Random.nextBoolean())
                    }
                    4 -> {
                        Path.Action.Quadratic(
                            Random.nextFloat(255),
                            Random.nextFloat(255),
                            Random.nextFloat(255),
                            Random.nextFloat(255),
                            Random.nextBoolean()
                        )
                    }
                    5 -> {
                        Path.Action.Curve(
                            Random.nextFloat(255),
                            Random.nextFloat(255),
                            Random.nextFloat(255),
                            Random.nextFloat(255),
                            Random.nextFloat(255),
                            Random.nextFloat(255),
                            Random.nextBoolean()
                        )
                    }
                    6 -> {
                        Path.Action.Arc(
                            Random.nextFloat(255),
                            Random.nextFloat(255),
                            Random.nextFloat(360),
                            Random.nextInt(0, 1),
                            Random.nextInt(0, 1),
                            Random.nextFloat(255),
                            Random.nextFloat(255),
                            Random.nextBoolean()
                        )
                    }
                    7 -> {
                        Path.Action.Smooth(
                            Random.nextFloat(255),
                            Random.nextFloat(255),
                            Random.nextFloat(255),
                            Random.nextFloat(255),
                            Random.nextBoolean()
                        )
                    }
                    8 -> {
                        Path.Action.SmoothQuadratic(
                            Random.nextFloat(255),
                            Random.nextFloat(255),
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
        return Path(actions)
    }

}