package com.wajihan.moviedb.data.movie.model.response

import com.google.gson.annotations.SerializedName
import com.wajihan.moviedb.domain.movie.model.Review

data class ReviewItem(

    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("url")
    val url: String?
) {

    fun toReview(): Review {
        return Review(author.orEmpty(), content.orEmpty(), id.orEmpty(), url.orEmpty())
    }
}