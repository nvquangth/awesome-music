package com.awesomemusic.ui.screen.playlist

import android.annotation.TargetApi
import android.app.Activity
import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.awesomemusic.R
import com.awesomemusic.data.model.Video
import com.awesomemusic.data.remote.FirestoreHelperImpl
import com.awesomemusic.data.remote.Network
import com.awesomemusic.data.repository.CloudRepository
import com.awesomemusic.data.repository.VideoRepository
import com.awesomemusic.ui.base.ItemVideoClickListenter
import kotlinx.android.synthetic.main.fragment_playlist.*

class PlaylistFragment : Fragment(), PlaylistContract.View {

    companion object {
        const val TAG = "PlaylistFragment"

        fun newInstance() = PlaylistFragment()
    }

    private lateinit var adapter: VideoAdapter
    private lateinit var itemClickListener: ItemVideoClickListenter
    lateinit var presenter: PlaylistPresenter

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is ItemVideoClickListenter) {
            itemClickListener = activity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater?.inflate(R.layout.fragment_playlist, container, false)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = PlaylistPresenter(
            view = this,
            cloudRepository = CloudRepository(firestoreHelper = FirestoreHelperImpl()),
            videoRepository = VideoRepository(api = Network().createMovieApi())
        )

        adapter = VideoAdapter(context, ::onRemoveItemVideoClick, ::onItemVideoClick)

        recyclerPlaylist.adapter = adapter

        presenter.apply {
            getPlaylist()
            listeningPlaylist()
        }

    }

    override fun showPlaylist(playlist: List<Video>) {
        adapter.submitList(playlist)
    }

    override fun showLoadingIndicator() {
    }

    override fun hideLoadingIndicator() {
    }

    override fun showError(throwable: Throwable) {
    }

    private fun onItemVideoClick(video: Video) {
        itemClickListener.onItemClick(TAG, video)
        presenter.addVideoToPlaying(video)
    }

    private fun onRemoveItemVideoClick(video: Video) {
        presenter.removeVideoFromPlaylist(video)
    }
}