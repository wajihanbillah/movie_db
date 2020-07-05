package com.wajihan.moviedb.data.movie.model.response.movie

import com.google.gson.annotations.SerializedName
import com.wajihan.moviedb.domain.movie.model.movie.SpokenLanguage

data class SpokenLanguageItem(
    @SerializedName("iso_639_1")
    val iso6391: String?,
    @SerializedName("name")
    val name: String?
) {

    fun toSpokenLanguage(): SpokenLanguage {
        return SpokenLanguage(iso6391.orEmpty(), name.orEmpty())
    }
}