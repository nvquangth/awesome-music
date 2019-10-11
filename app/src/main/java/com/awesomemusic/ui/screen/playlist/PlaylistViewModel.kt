package com.awesomemusic.ui.screen.playlist

import androidx.lifecycle.MutableLiveData
import com.awesomemusic.data.Constants
import com.awesomemusic.data.model.Video
import com.awesomemusic.data.repository.CloudRepository
import com.awesomemusic.ui.base.BaseViewModel

class PlaylistViewModel(private val cloudRepository: CloudRepository) : BaseViewModel() {

    val playlist = MutableLiveData<List<Video>>()

    fun getPlaylist() {
        cloudRepository.getPlaylist().get().addOnSuccessListener { result ->
            val videos = arrayListOf<Video>()
            for (document in result) {
                val video = Video(
                    id = document.getString(Constants.ID_FIELD),
                    videoId = document.getString(Constants.VIDEO_ID_FIELD) ?: "",
                    channelId = document.getString(Constants.CHANNEL_ID_FIELD),
                    channelTitle = document.getString(Constants.CHANNEL_TITLE_FIELD),
                    description = document.getString(Constants.DESCRIPTION_FIELD),
                    publishedAt = document.getString(Constants.PUBLISHED_AT_FIELD),
                    thumbnailUrl = document.getString(Constants.THUMBNAIL_URL_FIELD),
                    title = document.getString(Constants.TITLE_FIELD)
                )
                videos.add(video)
            }
            playlist.value = videos
        }.addOnFailureListener { exception ->
            onError(exception)
        }
    }
}