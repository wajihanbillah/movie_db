package com.wajihan.moviedb.utils.base.data.datastore

import com.google.gson.Gson

fun <T> singleApiError(): SingleApiErrorOperator<T> {
    return SingleApiErrorOperator(Gson())
}

