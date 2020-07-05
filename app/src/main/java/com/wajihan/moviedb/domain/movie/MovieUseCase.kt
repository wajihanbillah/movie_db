package com.wajihan.moviedb.domain.movie

import com.wajihan.moviedb.domain.movie.model.Genre
import com.wajihan.moviedb.domain.movie.model.Review
import com.wajihan.moviedb.domain.movie.model.Video
import com.wajihan.moviedb.domain.movie.model.movie.Movie
import io.reactivex.Single

interface MovieUseCase {

    fun getGenres(): Single<List<Genre>>

    fun getDiscoverMovies(genres: String, page: Int): Single<List<Movie>>

    fun getMovieDetail(movieId: Int): Single<Movie>

    fun getMovieReviews(movieId: Int, page: Int): Single<List<Review>>

    fun getMovieVideos(movieId: Int): Single<List<Video>>
}