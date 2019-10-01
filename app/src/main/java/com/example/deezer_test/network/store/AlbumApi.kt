package com.example.deezer_test.network.store

import com.example.deezer_test.model.Album
import com.example.deezer_test.model.Result
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface AlbumApi {

    @GET("user/2529/albums")
    fun getAlbums(@Query("index") index: Int?): Deferred<Response<Result<Album>?>>

}