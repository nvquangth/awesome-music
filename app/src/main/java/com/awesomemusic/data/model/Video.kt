package com.awesomemusic.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Video(
    val id: String? = null,
    @SerializedName("video_id")
    val videoId: String,
    val title: String? = null,
    @SerializedName("channel_id")
    val channelId: String? = null,
    @SerializedName("channel_title")
    val channelTitle: String? = null,
    val description: String? = null,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String? = null,
    @SerializedName("published_at")
    val publishedAt: String? = null
) : Parcelable