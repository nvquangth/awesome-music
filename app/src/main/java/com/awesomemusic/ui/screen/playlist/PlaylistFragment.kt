package com.awesomemusic.ui.screen.playlist

import android.os.Bundle
import androidx.lifecycle.Observer
import com.awesomemusic.R
import com.awesomemusic.data.model.Video
import com.awesomemusic.databinding.FragmentPlaylistBinding
import com.awesomemusic.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_playlist.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistFragment : BaseFragment<FragmentPlaylistBinding, PlaylistViewModel>() {
    companion object {
        const val TAG = "PlaylistFragment"

        fun newInstance() = PlaylistFragment()
    }

    override val viewModel: PlaylistViewModel by viewModel()
    override val layoutId: Int = R.layout.fragment_playlist

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = VideoAdapter(::onItemVideoClick)

        recyclerPlaylist.adapter = adapter

        viewModel.apply {
            playlist.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
        }

        viewModel.apply {
            getPlaylist()
            listeningPlaylist()
        }
    }

    private fun onItemVideoClick(video: Video) {

    }
}