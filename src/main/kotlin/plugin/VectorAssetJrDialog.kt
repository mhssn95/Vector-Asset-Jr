package plugin;

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerIconDefaults
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import icons.folder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import parser.SvgParser
import java.io.File
import javax.swing.JComponent
import kotlin.math.floor
import icons.error as errorIcon


class VectorAssetJrDialog(private val project: Project) : DialogWrapper(project, true) {

    private val svgParser = SvgParser()

    init {
        title = "Vector Asset Jr"
        setSize(700, 555)
        init()
    }

    @OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
    override fun createCenterPanel(): JComponent? {
        val panel = ComposePanel()
        val regex = Regex("\\D+")
        panel.setContent {
            var name = remember { mutableStateOf("") }
            var file = remember { mutableStateOf(File("")) }
            var width = remember { mutableStateOf(0.0) }
            var height = remember { mutableStateOf(0.0) }
            var opacity = remember { mutableStateOf(1f) }
            var aspect = remember { mutableStateOf(0.0) }
            var wIsBigger = remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()
            LaunchedEffect(file.value) {
                scope.launch(Dispatchers.IO) {
                    if (file.value.isValid()) {
                        val size = svgParser.getSvgSize(file.value)
                        if (size == null) {
                            //display error
                        } else {
                            width.value = size.width
                            height.value = size.height
                            aspect.value = maxOf(size.width, size.height) / minOf(size.width, size.height)
                            wIsBigger.value = size.width > size.height
                        }
                    }
                }
            }
            Column(Modifier.fillMaxSize().background(Color(59, 63, 65))) {
                Row(Modifier.fillMaxWidth().background(Color(75, 75, 75))) {
                    Text(
                        "Configure Vector Asset Jr",
                        color = Color.White,
                        fontSize = 32.sp,
                        modifier = Modifier.padding(32.dp)
                    )
                }
                Row(Modifier.fillMaxWidth().weight(1f)) {
                    Column(Modifier.weight(1f).padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Name:", color = Color.White, fontSize = 14.sp, modifier = Modifier.width(100.dp)
                            )
                            BasicTextField(
                                name.value,
                                { name.value = it },
                                textStyle = TextStyle(Color.White, 14.sp),
                                cursorBrush = SolidColor(Color.White),
                                singleLine = true,
                                decorationBox = @Composable { innerTextField ->
                                    Box(Modifier.padding(6.dp)) {
                                        innerTextField()
                                    }
                                },
                                modifier = Modifier.border(1.dp, Color(0xff646463)).background(Color(0xff44494A))
                                    .weight(1f)
                            )
                        }
                        Spacer(Modifier.height(14.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Path:", color = Color.White, fontSize = 14.sp, modifier = Modifier.width(100.dp)
                            )
                            BasicTextField(
                                file.value.path,
                                { file.value = File(it) },
                                textStyle = TextStyle(Color.White, 14.sp),
                                cursorBrush = SolidColor(Color.White),
                                singleLine = true,
                                decorationBox = @Composable { innerTextField ->
                                    Box(Modifier.padding(6.dp)) {
                                        Row {
                                            Box(Modifier.weight(1f)) {
                                                innerTextField()
                                            }
                                            Image(
                                                folder,
                                                "folder",
                                                colorFilter = ColorFilter.tint(Color(0xFF606A6F)),
                                                modifier = Modifier.size(18.dp).combinedClickable {
                                                    FileChooser.chooseFile(
                                                        FileChooserDescriptorFactory.createSingleFileDescriptor("svg"),
                                                        project, null
                                                    ) {
                                                        name.value = it.name
                                                        file.value = File(it.path)
                                                    }
                                                }.pointerHoverIcon(PointerIconDefaults.Hand)
                                            )
                                        }
                                    }
                                },
                                modifier = Modifier.border(1.dp, Color(0xff646463)).background(Color(0xff44494A))
                                    .weight(1f)
                            )
                        }
                        Spacer(Modifier.height(14.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Size:", color = Color.White, fontSize = 14.sp, modifier = Modifier.width(100.dp)
                            )
                            BasicTextField(
                                width.value.toString(),
                                {
                                    width.value = it.toDoubleOrNull() ?: width.value
                                    if (aspect.value != 0.0) {
                                        height.value = if (wIsBigger.value) {
                                            width.value / aspect.value
                                        } else {
                                            width.value * aspect.value
                                        }
                                    }
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                textStyle = TextStyle(Color.White, 14.sp),
                                cursorBrush = SolidColor(Color.White),
                                singleLine = true,
                                decorationBox = @Composable { innerTextField ->
                                    Box(Modifier.padding(6.dp)) {
                                        innerTextField()
                                    }
                                },
                                modifier = Modifier.border(1.dp, Color(0xff646463)).background(Color(0xff44494A))
                                    .width(50.dp)
                            )
                            Text(
                                "dp  X ",
                                color = Color.White,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                            BasicTextField(
                                height.value.toString(),
                                {
                                    height.value = it.toDoubleOrNull() ?: height.value
                                    if (aspect.value != 0.0) {
                                        width.value = if (wIsBigger.value) {
                                            height.value * aspect.value
                                        } else {
                                            height.value / aspect.value
                                        }
                                    }
                                },
                                textStyle = TextStyle(Color.White, 14.sp),
                                cursorBrush = SolidColor(Color.White),
                                singleLine = true,
                                decorationBox = @Composable { innerTextField ->
                                    Box(Modifier.padding(6.dp)) {
                                        innerTextField()
                                    }
                                },
                                modifier = Modifier.border(1.dp, Color(0xff646463)).background(Color(0xff44494A))
                                    .width(50.dp)
                            )
                            Text(
                                "dp",
                                color = Color.White,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                        }
                        Spacer(Modifier.height(14.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Opacity:", color = Color.White, fontSize = 14.sp, modifier = Modifier.width(100.dp)
                            )
                            Slider(
                                opacity.value,
                                {
                                    opacity.value = it
                                },
                                colors = SliderDefaults.colors(Color.White, activeTrackColor = Color.White.copy(0.5f)),
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                "${floor(opacity.value * 100).toInt()} %",
                                color = Color.White,
                                fontSize = 14.sp,
                                textAlign = TextAlign.End,
                                modifier = Modifier.width(50.dp).padding(start = 4.dp)
                            )
                        }
                    }
                    Column(
                        Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BoxWithConstraints(modifier = Modifier.size(250.dp)) {
                            Canvas(Modifier.fillMaxSize().background(Color.White)) {
                                repeat(32) { x ->
                                    repeat(32) { y ->
                                        drawRect(
                                            if (x % 2 == 0) {
                                                if (y % 2 == 0) {
                                                    Color.White
                                                } else {
                                                    Color(0xFFE0E0E0)
                                                }
                                            } else {
                                                if (y % 2 == 0) {
                                                    Color(0xFFE0E0E0)
                                                } else {
                                                    Color.White
                                                }
                                            },
                                            Offset(
                                                x * size.width / 32,
                                                y * size.width / 32
                                            ), size = Size(size.width / 32, size.width / 32)
                                        )
                                    }
                                }
                            }
                            if (file.value.isValid()) {
                                Image(
                                    loadSvgPainter(file.value, LocalDensity.current),
                                    "vector",
                                    modifier = Modifier.fillMaxSize().alpha(opacity.value)
                                )
                            }
                        }
                        Text(
                            "Vector Drawable Preview",
                            color = Color.White,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }

                var warning: String? = null
                var error: String? = null
                when {
                    file.value.path.isBlank() || file.value.path.endsWith('/') || file.value.isDirectory -> {
                        warning = "Please select a file"
                    }
                    !file.value.exists() -> {
                        error = "File ${file.value.path.split("/").last()} does not exist"
                    }
                    file.value.extension != "svg" -> {
                        error = "The specified asset could not be parsed. Please choose another asset."
                    }
                }
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    if (warning != null) {
                        Image(Icons.Default.Warning, "warning", colorFilter = ColorFilter.tint(Color(0xFFF0A732)))
                        Text(
                            warning,
                            color = Color.White,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    } else if (error != null) {
                        Image(errorIcon, "warning", colorFilter = ColorFilter.tint(Color(0xFFFF5E61)))
                        Text(
                            error,
                            color = Color.White,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
        return panel
    }

    private fun File.isValid(): Boolean {
        return exists() && extension == "svg"
    }

    private fun loadSvgPainter(file: File, density: Density): Painter =
        file.inputStream().buffered().use { loadSvgPainter(it, density) }
}