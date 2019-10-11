package com.awesomemusic.di

import com.awesomemusic.data.repository.CloudRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { CloudRepository(get()) }
}