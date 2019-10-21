package com.awesomemusic.ui.screen.main

import android.app.Fragment
import android.app.FragmentTransaction
import android.os.Bundle
import android.view.MenuItem
import com.awesomemusic.R
import com.awesomemusic.data.model.Video
import com.awesomemusic.ui.base.ItemVideoClickListenter
import com.awesomemusic.ui.base.MotionLayoutListener
import com.awesomemusic.ui.screen.player.PlayerFragment
import com.awesomemusic.ui.screen.playlist.PlaylistFragment
import com.awesomemusic.ui.screen.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.youtube.player.YouTubeBaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_playlist.*
import kotlinx.android.synthetic.main.fragment_search.*

class MainActivity : YouTubeBaseActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemReselectedListener,
    ItemVideoClickListenter,
    MotionLayoutListener {

    private var currentFragment: Fragment? = null
    private var playlistFragment: PlaylistFragment? = null
    private var searchFragment: SearchFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpBottomNavigation()

        playlistFragment = PlaylistFragment.newInstance()
        searchFragment = SearchFragment.newInstance()

//        fragmentManager?.beginTransaction()?.add(R.id.container, playlistFragment, PlaylistFragment.TAG)?.commit()
        fragmentManager?.beginTransaction()?.add(R.id.container, searchFragment, SearchFragment.TAG)?.commit()
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

    override fun onItemClick(video: Video) {
        val currentFragment = findFragment(PlayerFragment.TAG)
        val newFragment = PlayerFragment.newInstance(video)

        if (currentFragment == null) {
                addFragment(fragment = newFragment, TAG = PlayerFragment.TAG, addToBackStack = true)
        } else {
            if (currentFragment is PlayerFragment) {
                currentFragment.loadNewVideo(video)
            }
        }
    }

    override fun onMotionLayoutProgress(TAG: String, process: Float) {
        when(TAG) {
            PlayerFragment.TAG -> {
//                if (playlistFragment != null) {
//                    playlistFragment?.motionLayoutPlaylist?.progress = process
//                }
                if (searchFragment != null) {
                    searchFragment?.motionLayoutSearch?.progress = process
                }
            }
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
        fragment: Fragment, TAG: String?, addToBackStack: Boolean = false
    ) {
        fragmentManager.beginTransaction().add(R.id.container, fragment, TAG).apply {
            commitTransaction(this, addToBackStack)
        }
    }

    fun replaceFragment(
        fragment: Fragment, TAG: String?, addToBackStack: Boolean = false
    ) {
        fragmentManager.beginTransaction().replace(R.id.container, fragment, TAG).apply {
            commitTransaction(this, addToBackStack)
        }
    }

    fun findFragment(TAG: String): Fragment? {
        return fragmentManager.findFragmentByTag(TAG)
    }

    private fun commitTransaction(
        transaction: FragmentTransaction, addToBackStack: Boolean = false
    ) {
        if (addToBackStack) transaction.addToBackStack(null)
        transaction.commit()
    }
}
