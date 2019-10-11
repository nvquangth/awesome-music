package com.awesomemusic.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Video(
    val id: String? = null,
    val videoId: String,
    val title: String? = null,
    val channelId: String? = null,
    val channelTitle: String? = null,
    val description: String? = null,
    val thumbnailUrl: String? = null,
    val publishedAt: String? = null
) : Parcelable