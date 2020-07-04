package com.wajihan.moviedb.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.kennyc.view.MultiStateView
import com.wajihan.moviedb.R

fun circularProgressBar(context: Context): Drawable {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    return circularProgressDrawable
}

fun emptyString() = ""

fun MultiStateView.showLoadingState() {
    this.viewState = MultiStateView.ViewState.LOADING
}

fun MultiStateView.showErrorState() {
    this.viewState = MultiStateView.ViewState.ERROR
}

fun MultiStateView.showDefaultState() {
    this.viewState = MultiStateView.ViewState.CONTENT
}

fun MultiStateView.showEmptyState() {
    this.viewState = MultiStateView.ViewState.EMPTY
}

fun MultiStateView.showErrorState(
    errorMessage: String? = null,
    title: String? = null,
    drawable: Drawable? = null,
    errorAction: (() -> Unit)? = null
) {
    this.viewState = MultiStateView.ViewState.ERROR

    errorMessage?.let {
        val tvError =
            this.getView(MultiStateView.ViewState.ERROR)?.findViewById<TextView>(R.id.tv_error)
        tvError?.text = errorMessage
    }

    title?.let {
        val tvTitle =
            this.getView(MultiStateView.ViewState.ERROR)?.findViewById<TextView>(R.id.tv_title)
        tvTitle?.text = title
    }

    drawable?.let {
        val imgError =
            this.getView(MultiStateView.ViewState.ERROR)?.findViewById<ImageView>(R.id.img_error)
        imgError?.setImageDrawable(drawable)
    }

    val btnError =
        this.getView(MultiStateView.ViewState.ERROR)?.findViewById<Button>(R.id.btn_error)

    btnError?.setOnClickListener { errorAction?.invoke() }
}

fun AppCompatActivity.setupToolbar(
    toolbar: Toolbar?,
    title: String = emptyString(),
    isChild: Boolean = false
) {
    toolbar?.let {
        setSupportActionBar(it)

        if (!isChild) {
            it.navigationIcon = null
        }
    }

    if (supportActionBar != null) {
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(isChild)
    }
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun String?.orMinus(): String {
    return if (this.isNullOrEmpty()) "-" else this
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun View.onClick(listener: () -> Unit) {
    this.setOnClickListener { listener.invoke() }
}

fun Int?.orZero(): Int {
    return this ?: 0
}

fun Double?.orZero(): Double {
    return this ?: 0.0
}

fun Boolean?.orFalse(): Boolean {
    return this ?: false
}
