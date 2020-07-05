package com.wajihan.moviedb.data.movie.model.response.movie

import com.google.gson.annotations.SerializedName
import com.wajihan.moviedb.domain.movie.model.movie.ProductionCountry

data class ProductionCountryItem(
    @SerializedName("iso_3166_1")
    val iso31661: String?,
    @SerializedName("name")
    val name: String?
) {

    fun toProductionCountry(): ProductionCountry {
        return ProductionCountry(iso31661.orEmpty(), name.orEmpty())
    }
}