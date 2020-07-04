package com.wajihan.moviedb.di

import com.google.gson.Gson
import org.koin.dsl.module

val utilityModule = module {

    single { Gson() }

}
