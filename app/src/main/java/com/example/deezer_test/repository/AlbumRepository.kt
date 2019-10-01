package com.example.deezer_test.repository

import com.example.deezer_test.model.Album
import com.example.deezer_test.network.configuration.Result
import com.example.deezer_test.network.store.AlbumApi
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
class AlbumRepository @Inject constructor(retrofit: Retrofit) : BaseRepository() {

    private var albumApi: AlbumApi = retrofit.create(AlbumApi::class.java)

    suspend fun  getAlbums(index: Int?): Result<com.example.deezer_test.model.Result<Album>> {
        val request = albumApi.getAlbums(index)
        return getResponse(request)
    }

}

