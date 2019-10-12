package com.awesomemusic.ui.screen.player

import android.os.Bundle
import androidx.core.os.bundleOf
import com.awesomemusic.R
import com.awesomemusic.data.model.Video
import com.awesomemusic.databinding.FragmentPlayerBinding
import com.awesomemusic.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerFragment : BaseFragment<FragmentPlayerBinding, PlayerViewModel>() {
    companion object {
        const val TAG = "PlayerFragment"
        private const val BUNDLE_VIDEO = "BUNDLE_VIDEO"
        fun newInstance(video: Video) = PlayerFragment().apply {
            bundleOf(BUNDLE_VIDEO to video)
        }
    }

    override val viewModel: PlayerViewModel by viewModel()
    override val layoutId: Int = R.layout.fragment_player

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}