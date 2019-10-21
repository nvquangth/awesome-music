package com.awesomemusic.data.repository

import com.awesomemusic.data.model.Video
import com.awesomemusic.data.remote.VideoApi

class VideoRepository(
    private val api: VideoApi
) {
    fun getVideosByTitle(q: String) = api.getVideos(q)

    fun addVideoToPlaylist(video: Video) = api.addVideoToPlaylist(
        videoId = video.videoId,
        title = video.title ?: "",
        channelId = video.channelId ?: "",
        channelTitle = video.channelTitle ?: "",
        publishedAt = video.publishedAt ?: "",
        description = video.description ?: "",
        thumbnailUrl = video.thumbnailUrl ?: ""
    )
}