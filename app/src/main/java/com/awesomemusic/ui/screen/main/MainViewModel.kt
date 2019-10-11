package com.awesomemusic.ui.screen.main

import androidx.lifecycle.MutableLiveData
import com.awesomemusic.ui.base.BaseViewModel

class MainViewModel : BaseViewModel() {
    val defaultTab = MutableLiveData<Int>().apply { value = 1 }
}