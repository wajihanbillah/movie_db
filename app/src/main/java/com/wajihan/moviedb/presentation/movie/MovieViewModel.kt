package com.wajihan.moviedb.presentation.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wajihan.moviedb.domain.movie.MovieUseCase
import com.wajihan.moviedb.domain.movie.model.Genre
import com.wajihan.moviedb.domain.movie.model.Review
import com.wajihan.moviedb.domain.movie.model.Video
import com.wajihan.moviedb.domain.movie.model.movie.Movie
import com.wajihan.moviedb.utils.base.viewmodel.BaseResult
import com.wajihan.moviedb.utils.base.viewmodel.addTo
import com.wajihan.moviedb.utils.base.viewmodel.genericErrorHandler
import com.wajihan.moviedb.utils.base.viewmodel.singleScheduler
import io.reactivex.disposables.CompositeDisposable

class MovieViewModel(
    private val useCase: MovieUseCase,
    private val disposable: CompositeDisposable
) : ViewModel() {

    val genres = MutableLiveData<BaseResult<List<Genre>>>()
    val movieDetail = MutableLiveData<BaseResult<Movie>>()
    val movieVideos = MutableLiveData<BaseResult<List<Video>>>()

    init {
        genres.value = BaseResult.default()
        movieDetail.value = BaseResult.default()
        movieVideos.value = BaseResult.default()
    }

    fun getGenres() {
        genres.value = BaseResult.loading()
        useCase.getGenres()
            .compose(singleScheduler())
            .subscribe({
                genres.value = if (it.isNullOrEmpty()) BaseResult.empty() else BaseResult.success(it)
            }, { genericErrorHandler(it, genres) })
            .addTo(disposable)
    }

    fun getDiscoverMovies(genres: String, page: Int): MutableLiveData<BaseResult<List<Movie>>> {
        val dicoverMovies = MutableLiveData<BaseResult<List<Movie>>>()

        dicoverMovies.value = BaseResult.loading()
        useCase.getDiscoverMovies(genres, page)
            .compose(singleScheduler())
            .subscribe({
                dicoverMovies.value = if (it.isNullOrEmpty()) BaseResult.empty() else BaseResult.success(it)
            }, { genericErrorHandler(it, dicoverMovies) })
            .addTo(disposable)

        return dicoverMovies
    }

    fun getMovieDetail(movieId: Int) {
        movieDetail.value = BaseResult.loading()
        useCase.getMovieDetail(movieId)
            .compose(singleScheduler())
            .subscribe({
                movieDetail.value = if (it == null) BaseResult.empty() else BaseResult.success(it)
            }, { genericErrorHandler(it, movieDetail) })
            .addTo(disposable)
    }

    fun getMovieReviews(movieId: Int, page: Int): MutableLiveData<BaseResult<List<Review>>> {
        val movieReviews = MutableLiveData<BaseResult<List<Review>>>()
        movieReviews.value = BaseResult.loading()
        useCase.getMovieReviews(movieId, page)
            .compose(singleScheduler())
            .subscribe({
                movieReviews.value = if (it.isNullOrEmpty()) BaseResult.empty() else BaseResult.success(it)
            }, { genericErrorHandler(it, movieReviews) })
            .addTo(disposable)

        return movieReviews
    }

    fun getMovieVideos(movieId: Int) {
        movieVideos.value = BaseResult.loading()
        useCase.getMovieVideos(movieId)
            .compose(singleScheduler())
            .subscribe({
                movieVideos.value = if (it.isNullOrEmpty()) BaseResult.empty() else BaseResult.success(it)
            }, { genericErrorHandler(it, movieVideos) })
            .addTo(disposable)
    }

    override fun onCleared() {
        if (!disposable.isDisposed) disposable.dispose()
        super.onCleared()
    }
}