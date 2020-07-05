package com.wajihan.moviedb.utils

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.kennyc.view.MultiStateView
import com.wajihan.moviedb.BuildConfig
import com.wajihan.moviedb.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

fun MultiStateView.showEmptyState(
    errorMessage: String? = null,
    title: String? = null,
    drawable: Drawable? = null
) {
    this.viewState = MultiStateView.ViewState.EMPTY

    errorMessage?.let {
        val tvError =
            this.getView(MultiStateView.ViewState.EMPTY)?.findViewById<TextView>(R.id.tv_error)
        tvError?.text = errorMessage
    }

    title?.let {
        val tvTitle =
            this.getView(MultiStateView.ViewState.EMPTY)?.findViewById<TextView>(R.id.tv_title)
        tvTitle?.text = title
    }

    drawable?.let {
        val imgError =
            this.getView(MultiStateView.ViewState.EMPTY)?.findViewById<ImageView>(R.id.img_error)
        imgError?.setImageDrawable(drawable)
    }
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

fun String?.orMinus(): String {
    return if (this.isNullOrEmpty()) "-" else this
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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

fun String.toImageUrl(): String {
    return if (this.contains("http")) this else BuildConfig.BASE_IMAGE_URL + this.removePrefix("/")
}

fun String.convertDateFormat(currentFormat: String, newFormat: String, locale: Locale = Locale.getDefault()): String {
    return if (this.isNotEmpty()) {
        var simpleDateFormat = SimpleDateFormat(currentFormat, locale)
        val currentDate = simpleDateFormat.parse(this)
        simpleDateFormat = SimpleDateFormat(newFormat, locale)
        simpleDateFormat.format(currentDate ?: Date())
    } else {
        this
    }
}

fun getYoutubeVideoThumbnailUrl(youtubeId: String): String {
    return "https://img.youtube.com/vi/${youtubeId}/0.jpg"
}

fun openWebPage(context: Context, url: String) {
    if (url.isNotEmpty()) {

        val link =
            if (url.contains("https://") or url.contains("http://")) url
            else String.format(context.getString(R.string.format_url), url)
        val webpage: Uri = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    } else {
        context.showToast(context.getString(R.string.message_no_url))
    }
}

fun setTranslucentActivity(window: Window) {
    val w = window
    w.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
}