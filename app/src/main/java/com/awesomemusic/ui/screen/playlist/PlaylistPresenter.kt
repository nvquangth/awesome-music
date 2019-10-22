package com.awesomemusic.ui.screen.playlist

import android.util.Log
import com.awesomemusic.data.Constants
import com.awesomemusic.data.model.Video
import com.awesomemusic.data.repository.CloudRepository
import com.awesomemusic.data.repository.VideoRepository
import com.awesomemusic.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class PlaylistPresenter(
    private var view: PlaylistContract.View,
    private val cloudRepository: CloudRepository,
    private val videoRepository: VideoRepository
) : PlaylistContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private val scheduler = SchedulerProvider()

    override fun getPlaylist() {
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
                    title = document.getString(Constants.TITLE_FIELD),
                    timeRequest = document.getLong(Constants.TIME_REQUEST_FIELD)
                )
                videos.add(video)
            }
            view.showPlaylist(sortPlaylist(videos))
        }.addOnFailureListener { exception ->
            view.showError(exception)
        }
    }

    override fun listeningPlaylist() {
        cloudRepository.getPlaylist().addSnapshotListener { snapshot, err ->
            if (err != null) {

                return@addSnapshotListener
            }

            if (snapshot != null) {
                val videos = arrayListOf<Video>()
                for (document in snapshot.documents) {
                    val video = Video(
                        id = document.getString(Constants.ID_FIELD),
                        videoId = document.getString(Constants.VIDEO_ID_FIELD) ?: "",
                        channelId = document.getString(Constants.CHANNEL_ID_FIELD),
                        channelTitle = document.getString(Constants.CHANNEL_TITLE_FIELD),
                        description = document.getString(Constants.DESCRIPTION_FIELD),
                        publishedAt = document.getString(Constants.PUBLISHED_AT_FIELD),
                        thumbnailUrl = document.getString(Constants.THUMBNAIL_URL_FIELD),
                        title = document.getString(Constants.TITLE_FIELD),
                        timeRequest = document.getLong(Constants.TIME_REQUEST_FIELD)
                    )
                    videos.add(video)
                }
                view.showPlaylist(sortPlaylist(videos))
            }
        }
    }

    override fun removeVideoFromPlaylist(video: Video) {
        compositeDisposable.add(
            videoRepository.deleteVideoFromPlaylist(video)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    if (it.statusCode == 200) {
                        Log.d("Playlist: ", "Remove success")
                    } else {
                        Log.d("Playlist: ", "Remove failed")
                    }
                }, {
                    view.showError(it)
                })
        )
    }

    override fun addVideoToPlaying(video: Video) {
        compositeDisposable.add(
            videoRepository.addVideoToPlaying(video)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    if (it.statusCode == 200) {
                        Log.d("Playlist: ", "Add To Playing Success")
                        removeVideoFromPlaylist(video)
                    } else {
                        Log.d("Playlist: ", "Add To Playing Failed")
                    }
                }, {
                    view.showError(it)
                })
        )
    }

    override fun onStart() {

    }

    override fun onStop() {

    }

    private fun sortPlaylist(playlist: MutableList<Video>): List<Video> {
        playlist.sortWith(Comparator { video1, video2 ->
            val timeRequestVideo1 = video1.timeRequest ?: 0
            val timeRequestVideo2 = video2.timeRequest ?: 0
            (timeRequestVideo1 - timeRequestVideo2).toInt()
        })
        return playlist
    }
}