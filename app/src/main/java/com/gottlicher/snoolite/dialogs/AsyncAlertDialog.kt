package com.gottlicher.snoolite.dialogs


import android.app.AlertDialog
import android.content.Context
import kotlinx.coroutines.CompletableDeferred

enum class DialogResult {
    NONE, POSITIVE, NEGATIVE, NEUTRAL, DISMISSED, ERROR
}

class AsyncAlertDialog (
    private val title: Int? = null,
    private val message: Int? = null,
    private val positiveButtonText: Int? = null,
    private val negativeButtonText: Int? = null,
    private val neutralButtonText: Int? = null) {

    suspend fun show (context: Context): DialogResult {

        val completion = CompletableDeferred<DialogResult> ()

        val builder = AlertDialog.Builder (context)
        if (title != null) { builder.setTitle(title) }
        if (message != null) { builder.setMessage(message) }
        if (positiveButtonText != null) { builder.setPositiveButton(positiveButtonText) { _, _ -> completion.complete(DialogResult.POSITIVE)} }
        if (negativeButtonText != null) { builder.setNegativeButton(negativeButtonText) { _, _ -> completion.complete(DialogResult.NEGATIVE)} }
        if (neutralButtonText != null) { builder.setNeutralButton(neutralButtonText) { _, _ -> completion.complete(DialogResult.NEUTRAL)} }

        builder.create().show()
        return completion.await()
    }
}