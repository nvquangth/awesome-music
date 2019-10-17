package com.awesomemusic.ui.screen.main

import android.app.Fragment
import android.app.FragmentTransaction
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import com.awesomemusic.R
import com.awesomemusic.ui.screen.player.PlayerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_player.*

class MainActivity : YouTubeBaseActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemReselectedListener {

//    override val viewModel: MainViewModel by viewModel()
//    override val layoutId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpBottomNavigation()

//        addFragment(
//            fragment = PlayerFragment.newInstance(),
//            TAG = PlayerFragment.TAG
//        )

        fragmentManager?.beginTransaction()?.add(R.id.container, PlayerFragment.newInstance(), PlayerFragment.TAG)?.commit()


        Handler().postDelayed({
            val listFragment = fragmentManager?.findFragmentByTag(PlayerFragment.TAG)
            listFragment?.youTubePlayer?.initialize("AIzaSyDmVBc4vIy8hBBFnv3tB3VvhZwUewNqYjs", object : YouTubePlayer.OnInitializedListener {
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
                    p1?.cueVideo("90Y_gWG4sZY")
                }
            })

        }, 1000)
    }

    override fun onNavigationItemReselected(item: MenuItem) = when (item.itemId) {
        R.id.nav_home -> {

        }
        R.id.nav_playlist -> {

        }
        R.id.nav_trending -> {

        }
        R.id.nav_library -> {

        }
        else -> {

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.nav_home -> {

            true
        }
        R.id.nav_playlist -> {

            true
        }
        R.id.nav_trending -> {

            true
        }
        R.id.nav_library -> {

            true
        }
        else -> {

            false
        }
    }

    private fun setUpBottomNavigation() {
        bottomNavigation.apply {
            selectedItemId = R.id.nav_playlist
            setOnNavigationItemSelectedListener(this@MainActivity)
            setOnNavigationItemReselectedListener(this@MainActivity)
        }

    }

    fun addFragment(
        fragment: Fragment, TAG: String?, addToBackStack: Boolean = false,
        transit: Int = -1
    ) {
        fragmentManager.beginTransaction().add(R.id.container, fragment, TAG).apply {
            commitTransaction(this, addToBackStack, transit)
        }
    }

    fun replaceFragment(
        fragment: Fragment, TAG: String?, addToBackStack: Boolean = false,
        transit: Int = -1
    ) {
        fragmentManager.beginTransaction().replace(R.id.container, fragment, TAG).apply {
            commitTransaction(this, addToBackStack, transit)
        }
    }

    fun findFragment(TAG: String): Fragment? {
        return fragmentManager.findFragmentByTag(TAG)
    }

    private fun commitTransaction(
        transaction: FragmentTransaction, addToBackStack: Boolean = false,
        transit: Int = -1
    ) {
        if (addToBackStack) transaction.addToBackStack(null)
        if (transit != -1) transaction.setTransition(transit)
        transaction.commit()
    }
}
