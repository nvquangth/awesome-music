package com.awesomemusic.ui.base

import com.awesomemusic.data.model.Video

interface ItemVideoClickListenter {
    fun onItemClick(fromFragment: String, video: Video)
}