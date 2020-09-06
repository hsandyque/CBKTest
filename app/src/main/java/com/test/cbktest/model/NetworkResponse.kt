package com.test.cbktest.model

sealed class NetworkResponse<out T> {
    data class Success<out T>(val value: T): NetworkResponse<T>()
    data class GenericError(val code: Int? = null, val error: String? = null): NetworkResponse<Nothing>()
    object NetworkError: NetworkResponse<Nothing>()
}