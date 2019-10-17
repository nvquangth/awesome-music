package com.awesomemusic.ui.screen.player

import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.awesomemusic.R
import kotlinx.android.synthetic.main.fragment_player.*

class PlayerFragment: Fragment() {
    companion object {
        const val TAG = "PLAYER_FRAGMENT"

        fun newInstance() = PlayerFragment()
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

        btnExpanded.setOnClickListener {
            motionLayoutPlayer.transitionToEnd()
        }
        btnCollapsed.setOnClickListener {
            motionLayoutPlayer.transitionToStart()
        }
    }
}