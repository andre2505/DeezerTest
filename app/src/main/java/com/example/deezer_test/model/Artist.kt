package com.example.deezer_test.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * The class description here.
 *
 * @author Thomas Drège
 *
 */
class Artist : Serializable {

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("picture")
    var picture: String? = null

    @SerializedName("picture_small")
    var pictureSmall: String? = null

    @SerializedName("picture_medium")
    var pictureMedium: String? = null

    @SerializedName("picture_big")
    var pictureBig: String? = null

    @SerializedName("picture_xl")
    var pictureXl: String? = null

    @SerializedName("tracklist")
    var tracklist: String? = null

    @SerializedName("type")
    var type: String? = null

}