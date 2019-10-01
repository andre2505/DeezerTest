package com.example.deezer_test.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

/**
 * The class description here.
 *
 * @author Thomas Drège
 *
 */
class Album : Serializable {

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("link")
    var link: String? = null

    @SerializedName("cover")
    var cover: String? = null

    @SerializedName("cover_small")
    var coverSmall: String? = null

    @SerializedName("cover_medium")
    var coverMedium: String? = null

    @SerializedName("cover_big")
    var coverBig: String? = null

    @SerializedName("cover_xl")
    var coverXL: String? = null

    @SerializedName("nb_tracks")
    var nbTracks: Int? = null

    @SerializedName("release_date")
    var releaseDate: Date? = null

    @SerializedName("record_type")
    var recordType: String? = null

    @SerializedName("available")
    var available: Boolean? = null

    @SerializedName("tracklist")
    var tracklist: String? = null

    @SerializedName("explicit_lyrics")
    var explicitLyrics: String? = null

    @SerializedName("time_add")
    var timeAdd: Long? = null

    @SerializedName("artist")
    var artist: Artist? = null

    @SerializedName("type")
    var type: String? = null

    @SerializedName("alternative")
    var alternative: Album? = null

    var listAlbum:MutableList<Album>? = null

}