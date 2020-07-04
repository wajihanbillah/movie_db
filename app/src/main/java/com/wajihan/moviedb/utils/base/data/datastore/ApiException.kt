package com.wajihan.moviedb.utils.base.data.datastore

import com.wajihan.moviedb.utils.base.data.datastore.ApiError

class ApiException(val apiError: ApiError? = null, override var response: retrofit2.Response<*>) :
    ResponseException(response)