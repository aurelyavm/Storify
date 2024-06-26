package com.example.submissionawalstoryapp.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResult(

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("userId")
    val userId: String?,

    @field:SerializedName("token")
    val token: String?
) : Parcelable
