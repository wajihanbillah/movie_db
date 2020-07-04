package com.wajihan.moviedb.data.movie

import com.wajihan.moviedb.data.movie.model.response.GenreItem
import com.wajihan.moviedb.data.movie.model.response.MovieItem
import com.wajihan.moviedb.data.movie.model.response.ReviewItem
import com.wajihan.moviedb.data.movie.remote.MovieApi
import com.wajihan.moviedb.utils.base.data.datastore.singleApiError
import io.reactivex.Single

class MovieDataStore(api: MovieApi) : MovieRepository {
    override val dbService = null

    override val webService = api

    override fun getGenres(): Single<List<GenreItem>> {
        return webService.getGenres()
            .lift(singleApiError())
            .map { it.genres }
    }

    override fun getDiscoverMovies(genres: String, page: Int): Single<List<MovieItem>> {
        return webService.getDiscoverMovies(genres, page)
            .lift(singleApiError())
            .map { it.results }
    }

    override fun getMovieDetail(movieId: Int): Single<MovieItem> {
        return webService.getMovieDetail(movieId)
            .lift(singleApiError())
            .map { it }
    }

    override fun getMovieReviews(movieId: Int, page: Int): Single<List<ReviewItem>> {
        return webService.getMovieReviews(movieId, page)
            .lift(singleApiError())
            .map { it.results }
    }
}