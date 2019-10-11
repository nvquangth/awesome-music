package com.awesomemusic.di

import com.awesomemusic.utils.BaseSchedulerProvider
import com.awesomemusic.utils.SchedulerProvider
import org.koin.dsl.module

val rxModule = module {
    single<BaseSchedulerProvider> { SchedulerProvider() }
}