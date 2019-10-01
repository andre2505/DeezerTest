package com.example.deezer_test.utils

import androidx.annotation.Nullable
import androidx.databinding.InverseMethod
import com.example.deezer_test.model.Track
import java.text.SimpleDateFormat
import java.util.*

/**
 * The class description here.
 *
 * @author Thomas Drège
 *
 */
object ConverterDate {

    @JvmStatic
    @InverseMethod("stringToDate")
    @Nullable
    fun DateToString(date: Date?): String? {
        if (date != null) {
            return SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date)
        }
        return null
    }


    @JvmStatic
    fun secondToHour(seconds: Long): String? {

        val sec = seconds % 60
        val minutes = seconds % 3600 / 60
        val hours = seconds % 86400 / 3600

        if (hours == 0.toLong()) {
            return String.format("%02d:%02d", minutes, sec)
        }
        return String.format("%02d:%02d:%02d", hours, minutes, sec)
    }

    @JvmStatic
    fun totalAlbum(tracks: List<Track>): String? {

        var totalAlbumTime = 0

        tracks.forEach { tracklist ->
            totalAlbumTime += tracklist.duration!!
        }

        return secondToHour(totalAlbumTime.toLong())
    }

    @JvmStatic
    fun stringToDate(value: String): Date? {
        return Date()
    }

    @JvmStatic
    @InverseMethod("stringToDouble")
    fun doubleToString(price: Double?): String? {
        if (price != null) {
            return price.toString()
        }
        return null
    }

    @JvmStatic
    fun stringToDouble(price: String?): Double? {
        if (price != null) {
            return price.toDouble()
        }
        return null
    }


}