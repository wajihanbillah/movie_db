package com.wajihan.moviedb.data.movie.remote

import com.wajihan.moviedb.data.movie.model.response.GenreItem
import com.wajihan.moviedb.data.movie.model.response.MovieItem
import com.wajihan.moviedb.data.movie.model.response.ReviewItem
import com.wajihan.moviedb.utils.base.data.ApiResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiClient {
    @GET("genre/movie/list")
    fun getGenres(): Single<Response<ApiResponse<List<GenreItem>>>>

    @GET("discover/movie")
    fun getDiscoverMovies(@Query("with_genres") genres: String, @Query("page") page: Int): Single<Response<ApiResponse<List<MovieItem>>>>

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movieId: Int): Single<Response<MovieItem>>

    @GET("movie/{movie_id}/reviews")
    fun getMovieReviews(@Path("movie_id") movieId: Int, @Query("page") page: Int): Single<Response<ApiResponse<List<ReviewItem>>>>
}