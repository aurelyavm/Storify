package com.example.submissionawalstoryapp.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PagingStory(
    @field:SerializedName("error")
    var error: String,

    @field:SerializedName("message")
    var message: String,

    @field:SerializedName("listStory")
    var listStory: List<DetailStoryList>
) : Parcelable