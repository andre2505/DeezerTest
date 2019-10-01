package com.example.deezer_test.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import com.example.deezer_test.model.Album
import com.example.deezer_test.network.configuration.Result
import com.example.deezer_test.repository.AlbumRepository
import com.example.deezer_test.ui.base.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The class description here.
 *
 * @author Thomas Drège
 *
 */
open class HomeViewModel @Inject constructor(
    var mAlbumRepository: AlbumRepository
) : BaseViewModel() {

    var mListAlbum = MutableLiveData<List<Album>>()

    var mListAlbumCopy = mutableListOf<Album>()

    init {
        getAlbumsRequest(0)
        mListAlbumCopy.add(0, Album())
        mIsLoading.value = true
    }

    fun getAlbumsRequest(index: Int?) {
        GlobalScope.launch {
            mAlbumRepository.getAlbums(index).apply {
                when (this) {
                    is Result.Success -> {
                        mListAlbumCopy.addAll(this.data?.data as MutableList<Album>)
                        mListAlbum.postValue(this.data?.data as List<Album>)
                        mIsLoading.postValue(false)
                    }
                    is Result.Error -> {
                        mIsError.postValue(true)
                    }
                    is Result.ErrorNetwork -> {
                        mIsErrorNetwork.postValue(true)
                    }
                }
            }
        }
    }

}
