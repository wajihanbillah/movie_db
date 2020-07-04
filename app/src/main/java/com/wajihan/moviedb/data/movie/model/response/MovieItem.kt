package com.wajihan.moviedb.data.movie.model.response

import com.google.gson.annotations.SerializedName
import com.wajihan.moviedb.domain.movie.model.Movie
import com.wajihan.moviedb.utils.orFalse
import com.wajihan.moviedb.utils.orZero

data class MovieItem(
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?,
    @SerializedName("video")
    val video: Boolean?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("vote_average")
    val voteAverage: Int?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("release_date")
    val releaseDate: String?,

    //Movie Detail
    @SerializedName("budget")
    val budget: Int?,
    @SerializedName("genres")
    val genres: List<GenreItem>?,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<String>?,
    @SerializedName("production_countries")
    val productionCountries: List<String>?,
    @SerializedName("revenue")
    val revenue: Int?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<String>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tagline")
    val tagline: String?
) {

    fun toMovie(): Movie {
        return Movie(
            popularity.orZero(),
            voteCount.orZero(),
            video.orFalse(),
            posterPath.orEmpty(),
            id.orZero(),
            adult.orFalse(),
            backdropPath.orEmpty(),
            originalLanguage.orEmpty(),
            originalTitle.orEmpty(),
            genreIds.orEmpty(),
            title.orEmpty(),
            voteAverage.orZero(),
            overview.orEmpty(),
            releaseDate.orEmpty(),
            budget.orZero(),
            genres?.map { it.toGenre() }.orEmpty(),
            homepage.orEmpty(),
            imdbId.orEmpty(),
            productionCompanies.orEmpty(),
            productionCountries.orEmpty(),
            revenue.orZero(),
            runtime.orZero(),
            spokenLanguages.orEmpty(),
            status.orEmpty(),
            tagline.orEmpty()
        )
    }
}