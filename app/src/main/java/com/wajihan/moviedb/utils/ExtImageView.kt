package com.wajihan.moviedb.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wajihan.moviedb.R

fun ImageView.loadImageUrl(context: Context, url: String, isCenterCrop: Boolean = false) {
    val options = RequestOptions()
        .placeholder(circularProgressBar(context))
        .error(R.drawable.ic_image_error)

    if (isCenterCrop) options.centerCrop()

    Glide.with(context)
        .load(url)
        .apply(options)
        .into(this)
}