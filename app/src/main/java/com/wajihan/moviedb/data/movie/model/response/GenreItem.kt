package com.wajihan.moviedb.data.movie.model.response

import com.google.gson.annotations.SerializedName
import com.wajihan.moviedb.domain.movie.model.Genre
import com.wajihan.moviedb.utils.orZero

data class GenreItem(

    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
) {

    fun toGenre(): Genre {
        return Genre(id.orZero(), name.orEmpty())
    }
}