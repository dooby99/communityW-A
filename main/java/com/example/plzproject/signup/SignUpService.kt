package com.example.plzproject.signup

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SignUpService {
    @Headers("Content-Type: application/json")
    @POST("/memberRegister")
    fun addUserByEnqueue(@Body userInfo: SignUpDTO): Call<ResponseBody> // Call 은 흐름처리 기능을 제공해줌

}