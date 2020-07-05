package com.wajihan.moviedb.data.movie.model.response

import com.google.gson.annotations.SerializedName
import com.wajihan.moviedb.domain.movie.model.Video
import com.wajihan.moviedb.utils.orZero

data class VideoItem(
    @SerializedName("id")
    val id: String?,
    @SerializedName("iso_639_1")
    val iso6391: String?,
    @SerializedName("iso_3166_1")
    val iso31661: String?,
    @SerializedName("key")
    val key: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("site")
    val site: String?,
    @SerializedName("size")
    val size: Int?,
    @SerializedName("type")
    val type: String?
) {

    fun toVideo(): Video {
        return Video(
            id.orEmpty(),
            iso6391.orEmpty(),
            iso31661.orEmpty(),
            key.orEmpty(),
            name.orEmpty(),
            site.orEmpty(),
            size.orZero(),
            type.orEmpty()
        )
    }
}