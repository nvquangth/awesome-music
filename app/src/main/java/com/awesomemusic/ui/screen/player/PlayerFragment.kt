package com.awesomemusic.ui.screen.player

import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import com.awesomemusic.R
import com.awesomemusic.data.model.Video
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.fragment_player.*

class PlayerFragment: Fragment() {
    companion object {
        const val TAG = "PLAYER_FRAGMENT"
        private const val BUNDLE_VIDEO = "BUNDLE_VIDEO"

        fun newInstance(video: Video) = PlayerFragment().apply {
            arguments = bundleOf(BUNDLE_VIDEO to video)
        }
    }

    private var video: Video? = null
    private var player: YouTubePlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_player, container, false)
        return view
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        video = arguments.getParcelable(BUNDLE_VIDEO) ?: return

        youTubePlayer?.initialize("AIzaSyDmVBc4vIy8hBBFnv3tB3VvhZwUewNqYjs", object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Log.d("youtube_player", "failed")
            }

            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                player = p1
                player?.cueVideo(video?.videoId)
            }
        })

        bindView()

        btnClosePlayer.setOnClickListener {
            if (player?.isPlaying == true) {
                player?.pause()
            }
            player?.release()

            activity.onBackPressed()
        }

        btnExpanded.setOnClickListener {
            motionLayoutPlayer.transitionToEnd()
        }
        btnCollapsed.setOnClickListener {
            motionLayoutPlayer.transitionToStart()
        }
    }

    fun loadNewVideo(video: Video) {
        this.video = video
        player?.loadVideo(video.videoId)
        bindView()
    }

    private fun bindView() {
        txtVideoTitle.text = video?.title
        txtChannelTitle.text = video?.channelTitle
    }
}