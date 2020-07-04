package com.wajihan.moviedb.data.movie

import com.wajihan.moviedb.data.movie.model.response.GenreItem
import com.wajihan.moviedb.data.movie.model.response.MovieItem
import com.wajihan.moviedb.data.movie.model.response.ReviewItem
import com.wajihan.moviedb.utils.base.data.BaseRepository
import io.reactivex.Single

interface MovieRepository : BaseRepository {

    fun getGenres(): Single<List<GenreItem>>

    fun getDiscoverMovies(genres: String, page: Int): Single<List<MovieItem>>

    fun getMovieDetail(movieId: Int): Single<MovieItem>

    fun getMovieReviews(movieId: Int, page: Int): Single<List<ReviewItem>>
}