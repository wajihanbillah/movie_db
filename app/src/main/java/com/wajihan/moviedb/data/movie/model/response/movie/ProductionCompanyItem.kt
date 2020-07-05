package com.wajihan.moviedb.data.movie.model.response.movie

import com.google.gson.annotations.SerializedName
import com.wajihan.moviedb.domain.movie.model.movie.ProductionCompany
import com.wajihan.moviedb.utils.orZero

data class ProductionCompanyItem(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: String?
) {

    fun toProductionCompany(): ProductionCompany {
        return ProductionCompany(id.orZero(), logoPath.orEmpty(), name.orEmpty(), originCountry.orEmpty())
    }
}