package com.touhidapps.align

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.*
import com.intellij.openapi.project.Project

class StartingPoint : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        val editor: Editor? = e.getData(PlatformDataKeys.EDITOR)
        val project: Project? = e.getRequiredData(CommonDataKeys.PROJECT)

        // select full line if half selected
        editor?.selectionModel?.selectionStartPosition?.line?.let { posStart ->
            editor?.selectionModel?.selectionEndPosition?.line?.let { posEnd ->
                editor?.visualPositionToOffset(VisualPosition(posStart, 0))?.let {  startOffset ->
                    editor?.document?.getLineEndOffset(posEnd)?.let {  endOffset ->
                        editor?.selectionModel?.setSelection(startOffset, endOffset)
                    }
                }
            }
        }

        editor?.selectionModel?.selectedText?.let {

//             Work off of the primary caret to get the selection info
//             Work off of the primary caret to get the selection info
            val primaryCaret: Caret = editor.caretModel.primaryCaret
            val start: Int = primaryCaret.selectionStart
            val end: Int = primaryCaret.selectionEnd

            var a = ""
            MyStringProcess().getResultAlignedArray(it).forEach {
                a += "$it\n"
            }

            if (a.endsWith("\n")){
                a = a.removeSuffix("\n")
            }

            WriteCommandAction.runWriteCommandAction(project) {
                editor.document.replaceString(start, end, a)
            }
            primaryCaret.removeSelection()

        }

    } // actionPerformed

}


