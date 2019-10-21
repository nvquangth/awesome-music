package com.awesomemusic.data.remote

import com.awesomemusic.data.model.BaseResponse
import com.awesomemusic.data.model.VideoResponse
import io.reactivex.Single
import retrofit2.http.*

interface VideoApi {

    @GET("/videos/search")
    fun getVideos(@Query("q") q: String): Single<VideoResponse>

    @FormUrlEncoded
    @POST("/playlists")
    fun addVideoToPlaylist(
        @Field("videoId") videoId: String,
        @Field("title") title: String,
        @Field("channelId") channelId: String,
        @Field("channelTitle") channelTitle: String,
        @Field("publishedAt") publishedAt: String,
        @Field("description") description: String,
        @Field("thumbnailUrl") thumbnailUrl: String
    ): Single<BaseResponse>
}