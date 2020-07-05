package com.wajihan.moviedb.domain.movie.model

data class Video(
    val id: String,
    val iso6391: String,
    val iso31661: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String
)