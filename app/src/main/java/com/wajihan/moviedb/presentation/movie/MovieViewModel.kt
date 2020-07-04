package com.wajihan.moviedb.presentation.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wajihan.moviedb.domain.movie.MovieUseCase
import com.wajihan.moviedb.domain.movie.model.Genre
import com.wajihan.moviedb.domain.movie.model.Movie
import com.wajihan.moviedb.domain.movie.model.Review
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
    val dicoverMovies = MutableLiveData<BaseResult<List<Movie>>>()
    val movieDetail = MutableLiveData<BaseResult<Movie>>()
    val movieReviews = MutableLiveData<BaseResult<List<Review>>>()

    init {
        genres.value = BaseResult.default()
        dicoverMovies.value = BaseResult.default()
        movieDetail.value = BaseResult.default()
        movieReviews.value = BaseResult.default()
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

    fun getDiscoverMovies(genres: String, page: Int) {
        dicoverMovies.value = BaseResult.loading()
        useCase.getDiscoverMovies(genres, page)
            .compose(singleScheduler())
            .subscribe({
                dicoverMovies.value = if (it.isNullOrEmpty()) BaseResult.empty() else BaseResult.success(it)
            }, { genericErrorHandler(it, dicoverMovies) })
            .addTo(disposable)
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

    fun getMovieReviews(movieId: Int, page: Int) {
        movieReviews.value = BaseResult.loading()
        useCase.getMovieReviews(movieId, page)
            .compose(singleScheduler())
            .subscribe({
                movieReviews.value = if (it.isNullOrEmpty()) BaseResult.empty() else BaseResult.success(it)
            }, { genericErrorHandler(it, movieReviews) })
            .addTo(disposable)
    }

    override fun onCleared() {
        if (!disposable.isDisposed) disposable.dispose()
        super.onCleared()
    }
}