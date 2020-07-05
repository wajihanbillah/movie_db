package com.wajihan.moviedb.domain.movie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre(
    val id: Int,
    val name: String,
    var isSelected: Boolean = false
) : Parcelable