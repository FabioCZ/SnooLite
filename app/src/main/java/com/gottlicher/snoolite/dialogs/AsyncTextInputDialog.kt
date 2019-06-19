package com.gottlicher.snoolite.dialogs

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import kotlinx.coroutines.CompletableDeferred
import org.jetbrains.anko.hintResource

class InputDialogResult (
    val dialogResult: DialogResult,
    val text: String?)

class AsyncInputDialog (
    private val title: Int,
    private val hintText: Int,
    private val positiveButtonText: Int? = null,
    private val negativeButtonText: Int? = null,
    private val neutralButtonText: Int? = null) {

    suspend fun show (context: Context): InputDialogResult {

        val completion = CompletableDeferred<InputDialogResult> ()

        val builder = AlertDialog.Builder (context)
        builder.setTitle(title)
        val editText = makeEditText(context, hintText)
        builder.setView(editText)

        if (positiveButtonText != null) { builder.setPositiveButton(positiveButtonText) { _, _ -> completion.complete(
            InputDialogResult(DialogResult.POSITIVE, editText.text.toString())
        )} }

        if (negativeButtonText != null) { builder.setNegativeButton(negativeButtonText) { _, _ -> completion.complete(
            InputDialogResult(DialogResult.NEGATIVE, editText.text.toString())
        )} }

        if (neutralButtonText != null) { builder.setNegativeButton(neutralButtonText) { _, _ -> completion.complete(
            InputDialogResult(DialogResult.NEUTRAL, editText.text.toString())
        )} }

        builder.create().show()
        return completion.await()
    }

    private fun makeEditText(context: Context, hint: Int) : EditText {
        val editText = EditText (context)
        editText.hintResource = hint
        return editText;
    }
}