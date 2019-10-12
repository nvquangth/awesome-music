package com.awesomemusic.ui.screen.main

import android.os.Bundle
import android.view.MenuItem
import com.awesomemusic.R
import com.awesomemusic.databinding.ActivityMainBinding
import com.awesomemusic.ui.base.BaseActivity
import com.awesomemusic.ui.screen.playlist.PlaylistFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemReselectedListener {

    override val viewModel: MainViewModel by viewModel()
    override val layoutId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpBottomNavigation()

        addFragment(
            fragment = PlaylistFragment.newInstance(),
            TAG = PlaylistFragment.TAG
        )
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
}
