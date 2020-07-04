package com.wajihan.moviedb.utils.base.data

interface BaseRepository {
    val webService: WebApi?
    val dbService: LocalDb?
}
