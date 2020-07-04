package com.wajihan.moviedb.utils.base.data

import com.google.gson.annotations.SerializedName

class ApiResponse<T> {

    @SerializedName("code")
    val code: String? = null
    @SerializedName("status")
    val status: String? = null
    @SerializedName("results")
    val results: T? = null
    @SerializedName("genres")
    val genres: T? = null
}