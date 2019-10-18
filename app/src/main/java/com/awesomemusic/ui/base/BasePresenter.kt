package com.awesomemusic.ui.base

interface BasePresenter<T: BaseView> {

    fun onStart()

    fun onStop()
}