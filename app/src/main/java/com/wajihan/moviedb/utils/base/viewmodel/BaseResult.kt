package com.wajihan.moviedb.utils.base.viewmodel

import com.wajihan.moviedb.utils.emptyString

sealed class BaseResult<T> {
    class Loading<T> : BaseResult<T>()
    class Default<T> : BaseResult<T>()
    class Empty<T> : BaseResult<T>()
    class Unauthorized<T>(val message: String = emptyString()) : BaseResult<T>()
    data class Success<T>(val data: T) : BaseResult<T>()
    data class Failure<T>(val throwable: Throwable?, val message: String?) : BaseResult<T>()

    companion object {
        fun <T> loading(): BaseResult<T> = Loading()
        fun <T> default(): BaseResult<T> = Default()
        fun <T> success(data: T): BaseResult<T> = Success(data)
        fun <T> empty(): BaseResult<T> = Empty()
        fun <T> fail(throwable: Throwable, message: String?): BaseResult<T> =
            Failure(throwable, message)
        fun <T> fail(message: String?): BaseResult<T> = Failure(null, message)
    }
}