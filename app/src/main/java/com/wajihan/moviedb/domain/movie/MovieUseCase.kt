package com.wajihan.moviedb.domain.movie

import com.wajihan.moviedb.data.movie.model.response.MovieItem
import com.wajihan.moviedb.data.movie.model.response.ReviewItem
import com.wajihan.moviedb.domain.movie.model.Genre
import com.wajihan.moviedb.domain.movie.model.Movie
import com.wajihan.moviedb.domain.movie.model.Review
import io.reactivex.Single

interface MovieUseCase {

    fun getGenres(): Single<List<Genre>>

    fun getDiscoverMovies(genres: String, page: Int): Single<List<Movie>>

    fun getMovieDetail(movieId: Int): Single<Movie>

    fun getMovieReviews(movieId: Int, page: Int): Single<List<Review>>
}