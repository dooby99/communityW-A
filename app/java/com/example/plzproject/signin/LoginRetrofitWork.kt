package com.example.plzproject.signin

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.plzproject.BoardActivity
import com.example.plzproject.LoginActivity
import com.example.plzproject.WriteActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
class LoginRetrofitWork(private val context: Context, private val userInfo: SignInRequestBody, private val activity:LoginActivity) {

//    private var cookieListener: ((String?) -> Unit)? = null
//
//    fun setCookieListener(listener: (String?) -> Unit) {
//        this.cookieListener = listener
//    }

    fun work() {
        val service = RetrofitAPI.emgMedService
        service.UserByEnqueue(userInfo)
            .enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
//                        val cookies = response.headers()["Set-Cookie"]
//                        cookieListener?.invoke(cookies)
                        val responseBody = response.body()
                        if (responseBody != null) {
                            val responseString = responseBody.string()
                            Log.d("response", response.toString())
                            Log.d("responseBody", responseBody.string())
                            Log.d("responseString", responseString)
                            when (responseString) {
                                "login success" -> {
                                    Toast.makeText(context, ". 로그인 성공 .", Toast.LENGTH_SHORT).show()
                                    Log.d("Login successful", "Login successful line")
                                    val intent = Intent(context, BoardActivity::class.java)
                                    context.startActivity(intent)
                                    activity.finish()
                                }
                                "0" -> {
                                    Toast.makeText(context, "아이디와 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show()
                                    Log.d("Login failed", "Invalid login response: 아이디와 비밀번호를 확인하세요.")
                                }
                                "9" -> {
                                    Toast.makeText(context, "통신 오류입니다.", Toast.LENGTH_SHORT).show()
                                    Log.d("Login failed", "통신 오류입니다.")
                                }
                                else -> {
                                    Toast.makeText(context, "오류 발생!!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(context, "통신 오류입니다.", Toast.LENGTH_SHORT).show()
                    Log.d("Login failed", t.message.toString())
                    Log.i("json", userInfo.toString())
                    Log.i("call", call.toString())
                }
            })
    }
}
//class RetrofitWork(private val context: Context, private val userInfo: SignInRequestBody) {
//    fun work() {
//        val service = RetrofitAPI.emgMedService
//        service.addUserByEnqueue(userInfo)
//            .enqueue(object : retrofit2.Callback<ResponseBody> {
//                override fun onResponse(
//                    call: Call<ResponseBody>,
//                    response: Response<ResponseBody>
//                ) {
//                    if (response.isSuccessful) {
//                        val responseBody = response.body()
//                        if (responseBody != null) {
//                            val responseString = responseBody.string()
//                            Log.d("response", response.toString())
//                            Log.d("responseBody", responseBody.string())
//                            Log.d("responseString", responseString)
//                            when (responseString) {
//                                "login success" -> {
//                                    Toast.makeText(context, ". 로그인 성공 .", Toast.LENGTH_LONG).show()
//                                    Log.d("Login successful", "Login successful line")
//                                    val intent = Intent(context, BoardActivity::class.java)
//                                    context.startActivity(intent)
//                                }
//
//                                "0" -> {
//                                    Toast.makeText(context, "아이디와 비밀번호를 확인하세요.", Toast.LENGTH_SHORT)
//                                        .show()
//                                    Log.d(
//                                        "Login failed",
//                                        "Invalid login response: 아이디와 비밀번호를 확인하세요."
//                                    )
//                                }
//
//                                "9" -> {
//                                    Toast.makeText(context, "통신 오류입니다.", Toast.LENGTH_SHORT).show()
//                                    Log.d("Login failed", "통신 오류입니다.")
//                                }
//
//                                else -> {
//                                    Toast.makeText(context, "오류 발생!!", Toast.LENGTH_SHORT).show()
//                                    // 다른 응답에 대한 처리
//                                }
//                            }
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                    Toast.makeText(context, "통신 오류입니다.", Toast.LENGTH_SHORT).show()
//                    Log.d("Login failed", t.message.toString())
//                    Log.i("json", userInfo.toString())
//                    Log.i("call", call.toString())
//                }
//            })
//    }
//}
