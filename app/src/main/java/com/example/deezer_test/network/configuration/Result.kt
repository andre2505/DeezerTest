package com.example.deezer_test.network.configuration

/**
 * The class description here.
 *
 * @author Thomas Drège
 *
 */
sealed class Result <out T:Any> {
    data class Success<out T : Any>(val data: T?) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data class ErrorNetwork(val exception: Exception) :Result<Nothing>()
}