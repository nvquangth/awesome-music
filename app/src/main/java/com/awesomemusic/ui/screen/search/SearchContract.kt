package com.awesomemusic.ui.screen.search

import com.awesomemusic.data.model.Video
import com.awesomemusic.ui.base.BasePresenter
import com.awesomemusic.ui.base.BaseView

interface SearchContract {
    interface View: BaseView {
        fun showVideos(videos: List<Video>)

        fun showQuery(q: String)

        fun showAddVideoToPlaylistSuccess(video: Video)

        fun showAddVideoToPlaylistFailed(video: Video)
    }

    interface Presenter: BasePresenter<View> {

        fun searchVideo(q: String)

        fun clearQuery()

        fun addVideoToPlaylist(video: Video)
    }
}