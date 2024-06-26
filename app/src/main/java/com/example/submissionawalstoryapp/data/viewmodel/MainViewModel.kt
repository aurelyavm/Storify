package com.example.submissionawalstoryapp.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.submissionawalstoryapp.data.repository.MainRepository
import com.example.submissionawalstoryapp.data.response.DetailStoryList
import com.example.submissionawalstoryapp.data.response.Login
import com.example.submissionawalstoryapp.data.response.LoginData
import com.example.submissionawalstoryapp.data.response.RegisterData
import okhttp3.MultipartBody
import okhttp3.RequestBody

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val stories: LiveData<List<DetailStoryList>> = mainRepository.stories

    val message: LiveData<String> = mainRepository.message

    val isLoading: LiveData<Boolean> = mainRepository.isLoading

    val isError: LiveData<Boolean> = mainRepository.isError

    val isSuccess: LiveData<Boolean> = mainRepository.isSuccess

    val userlogin: LiveData<Login> = mainRepository.userlogin

    fun login(loginData: LoginData) {
        mainRepository.getResponseLogin(loginData)
    }

    fun register(registDataUser: RegisterData) {
        mainRepository.getResponseRegister(registDataUser)
    }

    fun postCreateStory(
        photo: MultipartBody.Part,
        des: RequestBody,
        lat: Double?,
        lng: Double?,
        token: String
    ) {
        mainRepository.upload(photo, des, lat, lng, token)
    }

    @ExperimentalPagingApi
    fun getPagingStories(token: String): LiveData<PagingData<DetailStoryList>> {
        return mainRepository.getPagingStories(token).cachedIn(viewModelScope)
    }

    fun getStories(token: String) {
        mainRepository.getStories(token)
    }
}