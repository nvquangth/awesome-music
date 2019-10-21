package com.awesomemusic.data.remote

import com.awesomemusic.data.model.VideoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoApi {

    @GET("/videos/search")
    fun getVideos(@Query("q") q: String): Single<VideoResponse>
}