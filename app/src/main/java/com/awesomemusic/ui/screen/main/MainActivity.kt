package com.awesomemusic.ui.screen.main

import android.app.Fragment
import android.app.FragmentTransaction
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import com.awesomemusic.R
import com.awesomemusic.data.model.Video
import com.awesomemusic.ui.base.ItemVideoClickListenter
import com.awesomemusic.ui.base.MotionLayoutListener
import com.awesomemusic.ui.base.VideoTrackingListener
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
    MotionLayoutListener,
    VideoTrackingListener {

    companion object {
        const val FRAGMENT_TAG = "fragment_tag_"
    }

    private var currentPositionFragment = Tab.PLAYLIST.tab
    private lateinit var playlistFragment: Fragment
    private lateinit var searchFragment: Fragment
    private lateinit var playerFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpBottomNavigation()

        playlistFragment = PlaylistFragment.newInstance()
        searchFragment = SearchFragment.newInstance()
        playerFragment = PlayerFragment.newInstance()

        onTabClick(0)
        onTabClick(1)
//        addPlayerFragment()
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
            onTabClick(0)
            true
        }
        R.id.nav_playlist -> {
            onTabClick(1)
            hideKeyBoard()
            true
        }
        R.id.nav_trending -> {
//            onTabClick(2)
//            hideKeyBoard()
            true
        }
        R.id.nav_library -> {
//            onTabClick(3)
//            hideKeyBoard()
            true
        }
        else -> {
            false
        }
    }

    override fun onItemClick(fromFragment: String, video: Video) {

    }

    override fun onMotionLayoutProgress(TAG: String, process: Float) {
        when (TAG) {
            PlayerFragment.TAG -> {
                when (currentPositionFragment) {
                    Tab.HOME.tab -> searchFragment.motionLayoutSearch?.progress = process
                    Tab.PLAYLIST.tab -> playlistFragment.motionLayoutPlaylist?.progress = process
                    Tab.TRENDING.tab -> {

                    }
                    Tab.LIBRARY.tab -> {

                    }
                }
            }
        }
    }

    override fun onVideoFinished() {
        if (playlistFragment is PlaylistFragment) {
            (playlistFragment as PlaylistFragment).presenter.autoPlayNextVideo()
        }
    }

    private fun setUpBottomNavigation() {
        bottomNavigation.apply {
            selectedItemId = R.id.nav_playlist
            setOnNavigationItemSelectedListener(this@MainActivity)
            setOnNavigationItemReselectedListener(this@MainActivity)
        }

    }

    private fun addPlayerFragment() {
        addFragment(fragment = playerFragment, TAG = PlayerFragment.TAG, addToBackStack = true)
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

    private fun onTabClick(tab: Int): Boolean {
        val currentTag = getTabFragmentTag(currentPositionFragment)
        val newTag = getTabFragmentTag(tab)

        val fragmentManager = fragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val currentFragment = findFragment(currentTag)
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment)
        }

        var newFragment = findFragment(newTag)
        if (newFragment == null) {
            newFragment = newFragmentInstance(tab)
            if (newFragment.isAdded) {
                fragmentTransaction.show(newFragment)
            } else {
                fragmentTransaction.add(R.id.container, newFragment, newTag)
            }
        } else {
            fragmentTransaction.show(newFragment)
        }
        currentPositionFragment = tab
        fragmentTransaction.commit()
        return true
    }

    private fun getTabFragmentTag(position: Int): String {
        return FRAGMENT_TAG + position
    }

    private fun newFragmentInstance(position: Int): Fragment {
        return when (position) {
            Tab.HOME.tab -> searchFragment
            Tab.PLAYLIST.tab -> playlistFragment
            Tab.TRENDING.tab -> searchFragment
            Tab.LIBRARY.tab -> searchFragment
            else -> Fragment()
        }
    }

    private fun hideKeyBoard() {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    private fun showKeyBoard() {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }
}
