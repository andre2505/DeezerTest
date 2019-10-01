package com.example.deezer_test.ui.fragment.detail

import androidx.lifecycle.MutableLiveData
import com.example.deezer_test.model.Album
import com.example.deezer_test.model.Track
import com.example.deezer_test.network.configuration.Result
import com.example.deezer_test.repository.TrackRepository
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
open class DetailViewModel @Inject constructor(
    var mTrackRepository: TrackRepository
) : BaseViewModel() {

    var mListTrack = MutableLiveData<List<Track>>()

    var mAlbum = MutableLiveData<Album>()

    var mTrack = MutableLiveData<Track>()

    var mIsAlbumDuration = false

    fun getTracksRequest(id: Int?) {
        GlobalScope.launch {
            mTrackRepository.getTracks(id).apply {
                when (this) {
                    is Result.Success -> {
                        this.data?.data?.let {
                            mListTrack.postValue(this.data?.data as List<Track>)
                        } ?: kotlin.run {
                            mListTrack.postValue(null)
                        }
                    }
                    is Result.Error -> {
                    }
                    is Result.ErrorNetwork -> {

                    }
                }
            }
        }
    }

}
