package com.awesomemusic.ui.screen.player

import com.awesomemusic.data.model.Video
import com.awesomemusic.ui.base.BasePresenter
import com.awesomemusic.ui.base.BaseView

interface PlayerContract {
    interface Presenter: BasePresenter<View> {
        fun getPlaying()

        fun listeningPlaying()
    }

    interface View: BaseView {
        fun showPlaying(video: Video)
    }
}