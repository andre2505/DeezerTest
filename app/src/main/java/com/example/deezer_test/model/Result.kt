package com.example.deezer_test.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * The class description here.
 *
 * @author Thomas Drège
 *
 */
class Result<T>: Serializable {

    @SerializedName("data")
    var data: List<T>? = null
}