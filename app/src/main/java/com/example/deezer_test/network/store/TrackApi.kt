package com.example.deezer_test.network.store

import com.example.deezer_test.model.Result
import com.example.deezer_test.model.Track
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TrackApi {

    @GET("album/{id}/tracks")
    fun getTracks(@Path("id") id: Int?): Deferred<Response<Result<Track>?>>
}