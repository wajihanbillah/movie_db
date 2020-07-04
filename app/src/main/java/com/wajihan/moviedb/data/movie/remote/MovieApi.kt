package com.wajihan.moviedb.data.movie.remote

import com.wajihan.moviedb.data.movie.model.response.GenreItem
import com.wajihan.moviedb.data.movie.model.response.MovieItem
import com.wajihan.moviedb.data.movie.model.response.ReviewItem
import com.wajihan.moviedb.utils.base.data.ApiResponse
import com.wajihan.moviedb.utils.base.data.WebApi
import io.reactivex.Single
import retrofit2.Response

class MovieApi(private val apiClient: MovieApiClient) : WebApi, MovieApiClient {

    override fun getGenres(): Single<Response<ApiResponse<List<GenreItem>>>> {
        return apiClient.getGenres()
    }

    override fun getDiscoverMovies(genres: String, page: Int): Single<Response<ApiResponse<List<MovieItem>>>> {
        return apiClient.getDiscoverMovies(genres, page)
    }

    override fun getMovieDetail(movieId: Int): Single<Response<MovieItem>> {
        return apiClient.getMovieDetail(movieId)
    }

    override fun getMovieReviews(movieId: Int, page: Int): Single<Response<ApiResponse<List<ReviewItem>>>> {
        return apiClient.getMovieReviews(movieId, page)
    }
}