package com.awesomemusic.data.remote

import com.awesomemusic.data.model.BaseResponse
import com.awesomemusic.data.model.Video
import io.reactivex.Single
import retrofit2.http.*

interface VideoApi {

    @GET("/api/v1/video/search")
    fun getVideos(@Query("q") q: String): Single<List<Video>>

    @FormUrlEncoded
    @POST("/api/v1/playlist")
    fun addVideoToPlaylist(
        @Field("videoId") videoId: String,
        @Field("title") title: String,
        @Field("channelId") channelId: String,
        @Field("channelTitle") channelTitle: String,
        @Field("publishedAt") publishedAt: String,
        @Field("description") description: String,
        @Field("thumbnailUrl") thumbnailUrl: String
    ): Single<BaseResponse>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/api/v1/playlist", hasBody = true)
    fun deleteVideoFromPlaylist(
        @Field("videoId") videoId: String,
        @Field("title") title: String,
        @Field("channelId") channelId: String,
        @Field("channelTitle") channelTitle: String,
        @Field("publishedAt") publishedAt: String,
        @Field("description") description: String,
        @Field("thumbnailUrl") thumbnailUrl: String
    ): Single<BaseResponse>

    @FormUrlEncoded
    @PUT("/api/v1/playing")
    fun addVideoToPlaying(
        @Field("videoId") videoId: String,
        @Field("title") title: String,
        @Field("channelId") channelId: String,
        @Field("channelTitle") channelTitle: String,
        @Field("publishedAt") publishedAt: String,
        @Field("description") description: String,
        @Field("thumbnailUrl") thumbnailUrl: String,
        @Field("timeRequest") timeRequest: Long
    ): Single<BaseResponse>
}