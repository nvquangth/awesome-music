package com.awesomemusic.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProvider : BaseSchedulerProvider {
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()

    override fun computation(): Scheduler = Schedulers.computation()

    override fun io(): Scheduler = Schedulers.io()
}