package com.touhidapps.align

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.ui.content.ContentManager


class StartingPoint : AnAction() {


    override fun actionPerformed(e: AnActionEvent) {

        val editor: Editor? = e.getData(PlatformDataKeys.EDITOR)
        val project: Project? = e.getRequiredData(CommonDataKeys.PROJECT)


        editor?.selectionModel?.selectedText?.let {
            println("$it")


//             Work off of the primary caret to get the selection info
//             Work off of the primary caret to get the selection info
            val primaryCaret: Caret = editor.caretModel.primaryCaret
            val start: Int = primaryCaret.selectionStart
            val end: Int = primaryCaret.selectionEnd



            var a = ""
            MyStringProcess().getResultAlignedArray(it).forEach {
                a += "$it\n"
            }


            WriteCommandAction.runWriteCommandAction(project) {
                editor.document.replaceString(start, end, a)
            }

            primaryCaret.removeSelection()


        }


    } // actionPerformed


}
















