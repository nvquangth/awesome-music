package com.awesomemusic.ui.base

interface BaseView {

    fun showLoadingIndicator()

    fun hideLoadingIndicator()

    fun showError(throwable: Throwable)
}