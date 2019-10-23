package com.awesomemusic.ui.screen.player

import com.awesomemusic.data.Constants
import com.awesomemusic.data.model.Video
import com.awesomemusic.data.repository.CloudRepository

class PlayerPresenter(
    private var view: PlayerContract.View,
    private val cloudRepository: CloudRepository
    ): PlayerContract.Presenter {

    var playerAlready = false
    var currentVideo: Video? = null

    override fun getPlaying() {
        cloudRepository.getPlaying().get().addOnSuccessListener {result ->
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
                currentVideo = video
                view.showPlaying(currentVideo!!)
                return@addOnSuccessListener
            }
        }.addOnFailureListener { exception ->
            view.showError(exception)
        }
    }

    override fun listeningPlaying() {
        cloudRepository.getPlaying().addSnapshotListener { snapshot, err ->
            if (err != null) {

                return@addSnapshotListener
            }

            if (snapshot != null) {
                for (document in snapshot.documents) {
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
                    currentVideo = video
                    view.showPlaying(currentVideo!!)
                    return@addSnapshotListener
                }
            }
        }
    }

    override fun onStart() {
    }

    override fun onStop() {
    }
}