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
import com.awesomemusic.R
import com.awesomemusic.data.model.Video
import com.awesomemusic.data.remote.FirestoreHelperImpl
import com.awesomemusic.data.repository.CloudRepository
import com.awesomemusic.ui.base.MotionLayoutListener
import com.awesomemusic.ui.base.VideoTrackingListener
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.fragment_player.*
import kotlin.math.abs

class PlayerFragment : Fragment(), PlayerContract.View {

    companion object {
        const val TAG = "PLAYER_FRAGMENT"
        fun newInstance() = PlayerFragment()
    }

    private var video: Video? = null
    private var player: YouTubePlayer? = null
    private var motionLayoutListener: MotionLayoutListener? = null
    private var presenter: PlayerPresenter? = null
    private var videoTrackingListener: VideoTrackingListener? = null

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is MotionLayoutListener) {
            motionLayoutListener = activity
        }
        if (activity is VideoTrackingListener) {
            videoTrackingListener = activity
        }
    }

    override fun onDetach() {
        if (motionLayoutListener != null) {
            motionLayoutListener = null
        }
        if (videoTrackingListener != null) {
            videoTrackingListener = null
        }
        super.onDetach()
    }

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater?.inflate(R.layout.fragment_player, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
                    presenter?.playerAlready = true
                    player = p1

                    if (presenter?.currentVideo != null) {
                        loadNewVideo(presenter!!.currentVideo!!)
                    }

                    player?.setPlayerStateChangeListener(object :
                        YouTubePlayer.PlayerStateChangeListener {
                        override fun onAdStarted() {
                        }

                        override fun onLoading() {
                        }

                        override fun onVideoStarted() {
                        }

                        override fun onLoaded(p0: String?) {
                        }

                        override fun onVideoEnded() {
                            videoTrackingListener?.onVideoFinished()
                        }

                        override fun onError(p0: YouTubePlayer.ErrorReason?) {
                        }

                    })
                }
            })

        presenter = PlayerPresenter(
            view = this,
            cloudRepository = CloudRepository(firestoreHelper = FirestoreHelperImpl())
        )

        presenter?.apply {
            getPlaying()
            listeningPlaying()
        }

        bindView()

        btnClosePlayer.setOnClickListener {
            if (player?.isPlaying == true) {
                player?.pause()
            }
            player?.release()

            activity.onBackPressed()
        }

        btnCollapsed.setOnClickListener {
            motionLayoutPlayer.transitionToStart()
        }

        layoutContent.setOnClickListener {
            // Do nothing
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

    override fun showPlaying(video: Video) {
        if (presenter?.playerAlready == true) {
            loadNewVideo(video)
        }
    }

    override fun showLoadingIndicator() {
    }

    override fun hideLoadingIndicator() {
    }

    override fun showError(throwable: Throwable) {
    }

    private fun loadNewVideo(video: Video) {
        this.video = video
        player?.loadVideo(video.videoId)
        bindView()
    }

    private fun bindView() {
        txtVideoTitle.text = video?.title
        txtChannelTitle.text = video?.channelTitle
    }
}