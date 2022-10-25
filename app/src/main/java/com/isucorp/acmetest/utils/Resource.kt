package com.isucorp.acmetest.utils

sealed class Resource<T>(val data: T? = null, val resId: Int? = null) {

    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T? = null, resId: Int? = null) : Resource<T>(data, resId)
    class Error<T>(data: T? = null, resId: Int? = null) : Resource<T>(data, resId)

}
