package com.awesomemusic.data.repository

import com.awesomemusic.data.remote.VideoApi

class VideoRepository(
    private val api: VideoApi
) {
    fun getVideosByTitle(q: String) = api.getVideos(q)
}