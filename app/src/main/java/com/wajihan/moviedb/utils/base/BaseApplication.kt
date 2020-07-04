package com.wajihan.moviedb.utils.base

import android.app.Application
import com.wajihan.moviedb.di.apiModule
import com.wajihan.moviedb.di.features.movieModule
import com.wajihan.moviedb.di.rxModule
import com.wajihan.moviedb.di.utilityModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(getDefinedModules())
        }
    }

    private fun getDefinedModules(): List<Module> {
        return listOf(
            apiModule,
            rxModule,
            utilityModule,
            movieModule
        )
    }
}