package com.example.demomvp.data.source.remote

interface OnFetchDataJsonListener<T> {

    fun onSuccess(data: List<T>?)

    fun onError(e: Exception?)
}
