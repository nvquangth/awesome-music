package com.awesomemusic.di

import com.awesomemusic.data.remote.FirestoreHelper
import com.awesomemusic.data.remote.FirestoreHelperImpl
import org.koin.dsl.module

val databaseModule = module {
    single<FirestoreHelper> { FirestoreHelperImpl() }
}