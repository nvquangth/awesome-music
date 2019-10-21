package com.awesomemusic.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BaseResponse(
    @SerializedName("status")
    val statusCode: Int? = null,
    val message: String? = null
) : Parcelable