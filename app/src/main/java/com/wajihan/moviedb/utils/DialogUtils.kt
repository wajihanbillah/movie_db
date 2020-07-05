package com.wajihan.moviedb.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import com.wajihan.moviedb.R

fun Context.showWhiteAlertDialog(
    title: String? = null,
    message: String? = null,
    positiveButton: Pair<String, () -> Unit>? = null,
    negativeButton: Pair<String, () -> Unit>? = null
) {

    val builder = AlertDialog.Builder(
        ContextThemeWrapper(
            this,
            android.R.style.Theme_Material_Light_Dialog_Alert
        )
    )

    builder.apply {
        if (title != null) setTitle(title)

        if (message != null) setMessage(message)

        if (negativeButton != null) {
            setNegativeButton(
                negativeButton.first
            ) { _, _ ->
                negativeButton.second.invoke()
            }
        }

        if (positiveButton != null) {
            setPositiveButton(
                positiveButton.first
            ) { _, _ ->
                positiveButton.second.invoke()
            }
        }

    }

    val dialog = builder.create()
    dialog.show()

    if (negativeButton != null) {
        val buttonNegative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        buttonNegative.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
        buttonNegative.setTextColor(ContextCompat.getColor(this, R.color.colorWarmGrey))
    }

    if (positiveButton != null) {
        val buttonPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
        buttonPositive.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
        buttonPositive.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
    }
}