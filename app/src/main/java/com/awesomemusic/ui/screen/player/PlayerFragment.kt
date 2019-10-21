package com.awesomemusic.ui.screen.player

import android.app.Activity
import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.core.os.bundleOf
import com.awesomemusic.R
import com.awesomemusic.data.model.Video
import com.awesomemusic.ui.base.MotionLayoutListener
import com.awesomemusic.ui.screen.main.MainActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_player.*
import kotlin.math.abs

class PlayerFragment : Fragment() {
    companion object {
        const val TAG = "PLAYER_FRAGMENT"
        private const val BUNDLE_VIDEO = "BUNDLE_VIDEO"

        fun newInstance(video: Video) = PlayerFragment().apply {
            arguments = bundleOf(BUNDLE_VIDEO to video)
        }
    }

    private var video: Video? = null
    private var player: YouTubePlayer? = null
    private var motionLayoutListener: MotionLayoutListener? = null

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is MotionLayoutListener) {
            motionLayoutListener = activity
        }
    }

    override fun onDetach() {
        if (motionLayoutListener != null) {
            motionLayoutListener = null
        }
        super.onDetach()
    }

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

        youTubePlayer?.initialize(
            "AIzaSyDmVBc4vIy8hBBFnv3tB3VvhZwUewNqYjs",
            object : YouTubePlayer.OnInitializedListener {
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

        motionLayoutPlayer.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun allowsTransition(p0: MotionScene.Transition?): Boolean = true

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                motionLayoutListener?.onMotionLayoutProgress(TAG, abs(p3))
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }

        })
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