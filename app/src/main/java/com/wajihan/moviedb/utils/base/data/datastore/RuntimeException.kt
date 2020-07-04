package com.wajihan.moviedb.utils.base.data.datastore

open class ResponseException(open var response: retrofit2.Response<*>) : RuntimeException()