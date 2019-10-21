package com.awesomemusic.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoResponse(
    @SerializedName("status")
    val statusCode: Int? = null,
    val message: String? = null,
    @SerializedName("result")
    val videos: List<Video>? = null
) : Parcelable