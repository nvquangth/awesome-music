package com.awesomemusic.di

import com.awesomemusic.ui.screen.main.MainViewModel
import com.awesomemusic.ui.screen.playlist.PlaylistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { PlaylistViewModel(get()) }
}