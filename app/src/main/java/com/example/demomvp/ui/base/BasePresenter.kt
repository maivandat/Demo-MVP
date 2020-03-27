package com.example.demomvp.ui.base

interface BasePresenter<T> {
    fun setView(view: T)
}