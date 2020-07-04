package com.wajihan.moviedb.di

import com.wajihan.moviedb.BuildConfig
import com.wajihan.moviedb.BuildConfig.BASE_URL
import com.wajihan.moviedb.data.HeaderInterceptor
import com.wajihan.moviedb.utils.base.data.OkHttpClientFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module

val apiModule = module {

    single {
        return@single OkHttpClientFactory.create(
            interceptors = HeaderInterceptor(),
            showDebugLog = BuildConfig.DEBUG
        )
    }

    single(named(BASE_URL)) { BASE_URL }
}