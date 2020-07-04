package com.wajihan.moviedb.utils.base.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonSyntaxException
import com.wajihan.moviedb.utils.base.data.datastore.ResponseException
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.net.SocketTimeoutException

fun <T> singleScheduler(
    subscriberScheduler: Scheduler = Schedulers.io(),
    observerScheduler: Scheduler = AndroidSchedulers.mainThread()
): SingleSchedulerTransformer<T> {
    return SingleSchedulerTransformer(subscriberScheduler, observerScheduler)
}

fun Disposable.addTo(disposable: CompositeDisposable) {
    disposable.add(this)
}

fun <T> genericErrorHandler(e: Throwable, baseResult: MutableLiveData<BaseResult<T>>) {
    // TODO: 28/11/18 define a proper Error Message
    when (e) {
        is SocketTimeoutException -> baseResult.value = BaseResult.fail(e, "Connection Timeout")
        is IOException -> baseResult.value = BaseResult.fail(e, "Connection IOException")
        is JsonSyntaxException -> baseResult.value = BaseResult.fail(e, "JSON Exception")
        is ResponseException -> baseResult.value = BaseResult.empty()
        else -> baseResult.value = BaseResult.fail(e, "An unknown error occurred")
    }
}