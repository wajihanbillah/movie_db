package com.wajihan.moviedb.domain.movie.model

data class Movie(
    val popularity: Double,
    val voteCount: Int,
    val video: Boolean,
    val posterPath: String,
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val genreIds: List<Int>,
    val title: String,
    val voteAverage: Int,
    val overview: String,
    val releaseDate: String,

    //Movie Detail
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val imdbId: String,
    val productionCompanies: List<String>,
    val productionCountries: List<String>,
    val revenue: Int,
    val runtime: Int,
    val spokenLanguages: List<String>,
    val status: String,
    val tagline: String
)