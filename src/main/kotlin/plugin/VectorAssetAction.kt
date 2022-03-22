package plugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class VectorAssetAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let {
            VectorAssetJrDialog(it).show()
        }
    }
}