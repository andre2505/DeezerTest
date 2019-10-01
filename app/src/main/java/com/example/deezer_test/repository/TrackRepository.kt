package com.example.deezer_test.repository

import com.example.deezer_test.model.Track
import com.example.deezer_test.network.configuration.Result
import com.example.deezer_test.network.store.TrackApi
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The class description here.
 *
 * @author Thomas Drège
 *
 */
@Singleton
class TrackRepository @Inject constructor(retrofit: Retrofit) : BaseRepository(){

    private var trackApi: TrackApi = retrofit.create(TrackApi::class.java)

    suspend fun getTracks(id: Int?): Result<com.example.deezer_test.model.Result<Track>> {
        val request = trackApi.getTracks(id)
        return getResponse(request)
    }

}