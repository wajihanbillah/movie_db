package com.wajihan.moviedb.di.features

import com.wajihan.moviedb.BuildConfig.BASE_URL
import com.wajihan.moviedb.data.movie.MovieDataStore
import com.wajihan.moviedb.data.movie.MovieRepository
import com.wajihan.moviedb.data.movie.remote.MovieApi
import com.wajihan.moviedb.data.movie.remote.MovieApiClient
import com.wajihan.moviedb.domain.movie.MovieInteractor
import com.wajihan.moviedb.domain.movie.MovieUseCase
import com.wajihan.moviedb.presentation.movie.MovieViewModel
import com.wajihan.moviedb.utils.base.data.ApiService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val movieModule = module {

    single {
        ApiService.createReactiveService(
            MovieApiClient::class.java,
            get(),
            get(named(BASE_URL))
        )
    }

    single { MovieApi(get()) }

    single<MovieRepository> { MovieDataStore(get()) }

    single<MovieUseCase> { MovieInteractor(get()) }

    viewModel { MovieViewModel(get(), get()) }
}