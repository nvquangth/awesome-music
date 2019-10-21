package com.awesomemusic.ui.screen.search

import com.awesomemusic.data.model.Video
import com.awesomemusic.data.repository.VideoRepository
import com.awesomemusic.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class SearchPresenter(
    private val view: SearchContract.View,
    private val repository: VideoRepository
) : SearchContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private val scheduler = SchedulerProvider()

    override fun searchVideo(q: String) {
        view.showLoadingIndicator()
        compositeDisposable.add(
            repository.getVideosByTitle(q)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    it.videos?.let {
                        view.showVideos(it)
                    }
                    view.hideLoadingIndicator()
                }, {
                    view.hideLoadingIndicator()
                    view.showError(it)
                })
        )
    }

    override fun clearQuery() {
        view.showQuery("")
    }

    override fun addVideoToPlaylist(video: Video) {
        compositeDisposable.add(
            repository.addVideoToPlaylist(video)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    if (it.statusCode == 200) {
                        view.showAddVideoToPlaylistSuccess(video)
                    } else if (it.statusCode == 500) {
                        view.showAddVideoToPlaylistFailed(video)
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
}