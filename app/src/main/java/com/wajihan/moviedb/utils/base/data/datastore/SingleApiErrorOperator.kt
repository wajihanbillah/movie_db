package com.wajihan.moviedb.utils.base.data.datastore

import com.google.gson.Gson
import com.wajihan.moviedb.utils.base.data.datastore.ApiError
import io.reactivex.SingleObserver
import io.reactivex.SingleOperator
import io.reactivex.disposables.Disposable
import retrofit2.Response
import java.io.IOException

class SingleApiErrorOperator<T>(private val gson: Gson) : SingleOperator<T, Response<T>> {


    override fun apply(observer: SingleObserver<in T>): SingleObserver<in Response<T>> {
        return object : SingleObserver<Response<T>> {

            override fun onSuccess(response: Response<T>) {
                if (!response.isSuccessful) {
                    try {
                        response.raw()
                        observer.onError(ApiException(response = response))
                    } catch (e: IOException) {
                        observer.onError(ResponseException(response))
                    }
                } else if (response.code() == 204) {
                    val apiError = ApiError(204, "Data Empty", "No Content")
                    observer.onError(ApiException(apiError, response))
                } else if (response.code() == 500) {
                    val apiError = ApiError(500, "Internal Server Error", "Error")
                    observer.onError(ApiException(apiError, response))
                } else if (response.body() == null) {
                    observer.onError(ApiException(response = response))
                } else {
                    observer.onSuccess(response.body()!!)
                }
            }

            override fun onSubscribe(d: Disposable) {
                observer.onSubscribe(d)
            }

            override fun onError(e: Throwable) {
                observer.onError(e)
            }
        }
    }
}