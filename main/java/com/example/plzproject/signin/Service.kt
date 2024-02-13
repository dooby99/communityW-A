package com.example.plzproject.signin

import com.example.plzproject.boardDetail.Board
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface Service {
    @Headers("Content-Type: application/json")
    @POST("/memberLogin")
    fun UserByEnqueue(@Body userInfo: SignInRequestBody): Call<ResponseBody>

    @Multipart
    @POST("/boardWrite")
    fun writePost(
        @Part("json") userInfo: RequestBody,
        @Part files: List<MultipartBody.Part>
    ): Call<ResponseBody>

    @GET("/boardWrite") // boardWrite 엔드포인트에 GET 요청을 보냅니다.
    fun checkLoginStatus(): Call<ResponseBody>

    @GET("/board/{link}")
    fun getLinkData(@Path("link") link: String): Call<ResponseBody>

    @GET("/board/delete/{link}")
    fun deleteBoard(@Path("link") link: String): Call<ResponseBody>

    @GET("/board/edit/{link}")
    fun editBoard(@Path("link") link: String): Call<ResponseBody>

    @POST("/board/edit/{link}")
    fun editPost(@Path("link") link: String, @Body board: Board): Call<ResponseBody>
}