package com.example.submissionawalstoryapp.utils

import com.example.submissionawalstoryapp.data.response.DetailStoryList
import com.example.submissionawalstoryapp.data.response.Login
import com.example.submissionawalstoryapp.data.response.LoginData
import com.example.submissionawalstoryapp.data.response.LoginResult
import com.example.submissionawalstoryapp.data.response.RegisterData

object DataDummy {

    fun generateDummyNewsEntity(): List<DetailStoryList> {
        val newsList = arrayListOf<DetailStoryList>()
        for (i in 0..100) {
            val stories = DetailStoryList(
                "Title $i",
                "this is name",
                "This is description",
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                "2022-02-22T22:22:22Z",
                i.toDouble(),
                i.toDouble(),
            )
            newsList.add(stories)
        }
        return newsList
    }

    fun generateDummyNewStories(): List<DetailStoryList> {
        val newsList = arrayListOf<DetailStoryList>()
        for (i in 0..100) {
            val stories = DetailStoryList(
                "Title $i",
                "this is name",
                "This is description",
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                "2022-02-22T22:22:22Z",
                i.toDouble(),
                i.toDouble(),
            )
            newsList.add(stories)
        }
        return newsList
    }

    fun generateDummyRequestLogin(): LoginData {
        return LoginData("testing123@gmail.com", "test1234")
    }

    fun generateDummyResponseLogin(): Login {
        val newLoginResult = LoginResult("This Testing", "testing", "this-is-token")
        return Login(newLoginResult, false, "Login successfully")
    }

    fun generateDummyRequestRegister(): RegisterData {
        return RegisterData("This Testing", "test123@gmail.com", "testing123")
    }

}