package com.example.submissionawalstoryapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.submissionawalstoryapp.data.database.StoryDatabase
import com.example.submissionawalstoryapp.data.remote.APIConfig
import com.example.submissionawalstoryapp.data.remote.APIService
import com.example.submissionawalstoryapp.data.response.DetailStoryList
import com.example.submissionawalstoryapp.data.response.LocationStory
import com.example.submissionawalstoryapp.data.response.Login
import com.example.submissionawalstoryapp.data.response.LoginData
import com.example.submissionawalstoryapp.data.response.Register
import com.example.submissionawalstoryapp.data.response.RegisterData
import com.example.submissionawalstoryapp.data.response.UploadStory
import com.example.submissionawalstoryapp.utils.wrapEspressoIdlingResource
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository(
    private val storyDatabase: StoryDatabase,
    private val apiService: APIService
) {
    private var _stories = MutableLiveData<List<DetailStoryList>>()
    var stories: LiveData<List<DetailStoryList>> = _stories

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _userLogin = MutableLiveData<Login>()
    var userlogin: LiveData<Login> = _userLogin

    fun getResponseLogin(loginData: LoginData) {
        wrapEspressoIdlingResource {
            _isLoading.value = true
            val api = APIConfig.getAPIService().loginUser(loginData)
            api.enqueue(object : Callback<Login> {
                override fun onResponse(
                    call: Call<Login>,
                    response: Response<Login>
                ) {
                    _isLoading.value = false
                    val responseBody = response.body()

                    if (response.isSuccessful) {
                        _userLogin.value = responseBody!!
                        _message.value = "Hello ${_userLogin.value!!.loginResult.name}!"
                    } else {
                        when (response.code()) {
                            401 -> _message.value =
                                "Wrong email or password, try again!"
                            408 -> _message.value =
                                "Poor internet connection, try again!"
                            else -> _message.value = "Error: " + response.message()
                        }
                    }
                }

                override fun onFailure(call: Call<Login>, t: Throwable) {
                    _isLoading.value = false
                    _message.value = "Error: " + t.message.toString()
                }

            })
        }
    }

    fun getResponseRegister(registDataUser: RegisterData) {
        wrapEspressoIdlingResource {
            _isLoading.value = true
            val api = APIConfig.getAPIService().registUser(registDataUser)
            api.enqueue(object : Callback<Register> {
                override fun onResponse(
                    call: Call<Register>,
                    response: Response<Register>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _message.value = "Successful created account!"
                    } else {
                        when (response.code()) {
                            400 -> _message.value =
                                "Email has registered, try again!"
                            408 -> _message.value =
                                "Poor internet connection, try again!"
                            else -> _message.value = "Error: " + response.message()
                        }
                    }
                }

                override fun onFailure(call: Call<Register>, t: Throwable) {
                    _isLoading.value = false
                    _message.value = "Error: " + t.message.toString()
                }

            })
        }
    }

    fun upload(
        photo: MultipartBody.Part,
        des: RequestBody,
        lat: Double?,
        lng: Double?,
        token: String
    ) {
        _isLoading.value = true
        val service = APIConfig.getAPIService().uploadStory(
            photo, des, lat?.toFloat(), lng?.toFloat(), "Bearer $token"
        )
        service.enqueue(object : Callback<UploadStory> {
            override fun onResponse(
                call: Call<UploadStory>,
                response: Response<UploadStory>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _message.value = responseBody.message
                    }
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<UploadStory>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message
            }
        })
    }

    fun getStories(token: String) {
        _isLoading.value = true
        val api = APIConfig.getAPIService().getLocationStory(32, 1, "Bearer $token")
        api.enqueue(object : Callback<LocationStory> {
            override fun onResponse(
                call: Call<LocationStory>,
                response: Response<LocationStory>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _stories.value = responseBody.listStory
                    }
                    _message.value = responseBody?.message.toString()

                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<LocationStory>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }

    @ExperimentalPagingApi
    fun getPagingStories(token: String): LiveData<PagingData<DetailStoryList>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = RemoteStoryMediator(storyDatabase, apiService, token),
            pagingSourceFactory = { storyDatabase.getListStoryDetailDao().getAllStories()}
        )
        return pager.liveData
    }

}