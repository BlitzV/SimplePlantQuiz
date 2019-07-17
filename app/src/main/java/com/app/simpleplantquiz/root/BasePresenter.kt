package com.app.simpleplantquiz.root

interface BasePresenter<T> {

    fun setView(view: T)

    fun dropView()
}