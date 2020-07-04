package com.wajihan.moviedb.domain.movie

import com.wajihan.moviedb.data.movie.MovieRepository
import com.wajihan.moviedb.domain.movie.model.Genre
import com.wajihan.moviedb.domain.movie.model.Movie
import com.wajihan.moviedb.domain.movie.model.Review
import io.reactivex.Single

class MovieInteractor(private val repository: MovieRepository) : MovieUseCase {

    override fun getGenres(): Single<List<Genre>> {
        return repository.getGenres().map {
            it.map { item ->
                item.toGenre()
            }
        }
    }

    override fun getDiscoverMovies(genres: String, page: Int): Single<List<Movie>> {
        return repository.getDiscoverMovies(genres, page).map {
            it.map { item ->
                item.toMovie()
            }
        }
    }

    override fun getMovieDetail(movieId: Int): Single<Movie> {
        return repository.getMovieDetail(movieId).map { item ->
            item.toMovie()
        }
    }

    override fun getMovieReviews(movieId: Int, page: Int): Single<List<Review>> {
        return repository.getMovieReviews(movieId, page).map {
            it.map { item ->
                item.toReview()
            }
        }
    }
}