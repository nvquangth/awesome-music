package com.awesomemusic.ui.screen.playlist

import com.awesomemusic.data.model.Video
import com.awesomemusic.ui.base.BasePresenter
import com.awesomemusic.ui.base.BaseView

interface PlaylistContract {
    interface View: BaseView {

        fun showPlaylist(playlist: List<Video>)
    }

    interface Presenter: BasePresenter<View> {

        fun getPlaylist()

        fun listeningPlaylist()
    }
}