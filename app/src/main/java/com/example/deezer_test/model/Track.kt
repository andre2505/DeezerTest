package com.example.deezer_test.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * The class description here.
 *
 * @author Thomas Drège
 *
 */
class Track : Serializable {

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("title_short")
    var titleShort: String? = null

    @SerializedName("title_version")
    var titleVersion: String? = null

    @SerializedName("isrc")
    var isrc: String? = null

    @SerializedName("link")
    var link: String? = null

    @SerializedName("duration")
    var duration: Int? = null

    @SerializedName("track_position")
    var trackPosition: Int? = null

    @SerializedName("disk_number")
    var diskNumer: Int? = null

    @SerializedName("rank")
    var rank: Int? = null

    @SerializedName("explicit_lyrics")
    var explicitLyrics: Boolean? = null

    @SerializedName("explicit_content_lyrics")
    var explicitContentLyrics: Int? = null

    @SerializedName("explicit_content_cover")
    var explicitContentCover: Int? = null

    @SerializedName("preview")
    var preview: String? = null

    @SerializedName("artist")
    var artist: Artist? = null

    @SerializedName("type")
    var type: String? = null
}