import com.squareup.kotlinpoet.*
import model.PathConverter
import model.Svg
import model.elements.Group
import model.elements.Path
import model.style.Style
import model.style.brush.fill.SolidColor
import java.io.File

class CodeGenerator {

    private val importSet = LinkedHashSet<Pair<String, String>>().apply {
        add("androidx.compose.ui.unit" to "dp")
    }

    fun generateVector(name: String, svg: Svg) {
        val args = ArrayList<Any>()
        val vector = PropertySpec.builder(name, ClassName("androidx.compose.ui.graphics.vector", "ImageVector")).getter(
            FunSpec.getterBuilder().addCode(
                """
                |if (_$name != null) {
                |   return _$name!!
                |}
                |
            """.trimMargin()
            ).addCode(
                """
                |_$name = ImageVector.Builder(%S, %L.dp, %L.dp, %Lf, %Lf)
            """.trimMargin(),
                name,
                svg.width,
                svg.height,
                svg.viewportWidth,
                svg.viewportHeight
            ).addCode(
                """${
                    svg.elements.joinToString("") {
                        when (it) {
                            is Group -> {
                                importSet.add("androidx.compose.ui.graphics.vector" to "group")
                                val groupCode = generateGroup(it)
                                args.addAll(groupCode.second)
                                ".${groupCode.first}"
                            }
                            is PathConverter -> {
                                importSet.add("androidx.compose.ui.graphics.vector" to "path")
                                val pathCode = generatePath(it.toPath())
                                args.addAll(pathCode.second)
                                ".${pathCode.first}"
                            }
                            else -> {
                                ""
                            }
                        }
                    }
                }.build()
                |return _$name!!
            """.trimMargin(), *args.toArray()
            ).build()
        ).build()

        val _vector = PropertySpec.builder(
            "_$name",
            ClassName("androidx.compose.ui.graphics.vector", "ImageVector").copy(nullable = true),
            KModifier.PRIVATE
        ).initializer("null").mutable().build()

        val builder = FileSpec.builder("", name)
            .addProperty(vector)
            .addProperty(_vector)
        importSet.forEach {
            builder.addImport(ClassName(it.first, it.second), "")
        }
        builder.build()
            .writeTo(File(".output/"))

    }

    private fun generateGroup(group: Group, level: Int = 0): Pair<String, Array<Any>> {
        val args = ArrayList<Any>()
        val code = """|${tap(level)}group {
            |${
            group.elements.joinToString("") {
                when (it) {
                    is Group -> {
                        val group = generateGroup(it, level + 1)
                        args.addAll(group.second)
                        group.first + "\n"
                    }
                    is PathConverter -> {
                        val path = generatePath(it.toPath(), level + 1)
                        args.addAll(path.second)
                        path.first + "\n"
                    }
                    else -> {
                        ""
                    }
                }
            }
        }${tap(level)}}
        """.trimMargin()
        return code.trimMargin() to args.toArray()
    }

    private fun generatePath(path: Path, level: Int = 0): Pair<String, Array<Any>> {
        val args = ArrayList<Any>()
        val code = "${tap(level)}path(${styleCode(path.style)}){\n${
            path.actions.joinToString("\n") {
                when (it) {
                    is Path.Action.Move -> {
                        args.addAll(listOf(it.x, it.y))
                        "|${tap(level + 1)}moveTo".isRelative(it.relative) + "(%Lf, %Lf)"
                    }
                    is Path.Action.HorizontalLine -> {
                        args.add(it.dx)
                        "|${tap(level + 1)}horizontalLineTo".isRelative(it.relative) + "(%Lf)"
                    }
                    is Path.Action.VerticalLine -> {
                        args.add(it.dy)
                        "|${tap(level + 1)}verticalLineTo".isRelative(it.relative) + "(%Lf)"
                    }
                    is Path.Action.LineTo -> {
                        args.addAll(listOf(it.x, it.y))
                        "|${tap(level + 1)}lineTo".isRelative(it.relative) + "(%Lf, %Lf)"
                    }
                    is Path.Action.Curve -> {
                        args.addAll(listOf(it.x1, it.y1, it.x2, it.y2, it.x3, it.y3))
                        "|${tap(level + 1)}curveTo".isRelative(it.relative) + "(%Lf, %Lf, %Lf, %Lf, %Lf, %Lf)"
                    }
                    is Path.Action.Arc -> {
                        args.addAll(
                            listOf(
                                it.horizontalRadius,
                                it.verticalRadius,
                                it.degree,
                                it.isMoreThanHalf == 0,
                                it.isPositiveArc == 0,
                                it.x,
                                it.y
                            )
                        )
                        "|${tap(level + 1)}arcTo".isRelative(it.relative) + "(%Lf, %Lf, %Lf, %L, %L, %Lf, %Lf)"
                    }
                    is Path.Action.Quadratic -> {
                        args.addAll(
                            listOf(
                                it.x1,
                                it.y1,
                                it.x2,
                                it.y2
                            )
                        )
                        "|${tap(level + 1)}quadTo".isRelative(it.relative) + "(%Lf, %Lf, %Lf, %Lf)"
                    }
                    is Path.Action.Smooth -> {
                        args.addAll(
                            listOf(
                                it.x1,
                                it.y1,
                                it.x2,
                                it.y2
                            )
                        )
                        "|${tap(level + 1)}reflectiveCurveTo".isRelative(it.relative) + "(%Lf, %Lf, %Lf, %Lf)"
                    }
                    is Path.Action.SmoothQuadratic -> {
                        args.addAll(
                            listOf(
                                it.x,
                                it.y
                            )
                        )
                        "|${tap(level + 1)}reflectiveQuadTo".isRelative(it.relative) + "(%Lf, %Lf)"
                    }
                    Path.Action.Close -> {
                        "|${tap(level + 1)}close()"
                    }
                }
            }
        }\n${tap(level)}}".trimMargin()
        return code to args.toArray()
    }

    private fun String.isRelative(relative: Boolean): String {
        if (relative) {
            return "${this}Relative"
        }
        return this
    }

    private fun styleCode(style: Style): String {
        val code = StringBuilder()
        let { style.fill?:SolidColor() }.let {
            "fill=\"${code.append((it as SolidColor).toHex())}\""
        }
        return code.toString()
    }

    private fun tap(count: Int): String {
        return (1..count).joinToString("") { "\t" }
    }

}