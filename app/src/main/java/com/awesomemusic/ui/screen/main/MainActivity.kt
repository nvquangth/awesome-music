package com.awesomemusic.ui.screen.main

import android.os.Bundle
import com.awesomemusic.R
import com.awesomemusic.databinding.ActivityMainBinding
import com.awesomemusic.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val viewModel: MainViewModel by viewModel()
    override val layoutId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation() {
        bottomNavigation.selectedItemId = R.id.nav_playlist
    }
}
