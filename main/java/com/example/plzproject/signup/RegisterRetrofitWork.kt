package com.example.plzproject.signup

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.plzproject.RegsisterActivity
import com.example.plzproject.signin.RetrofitAPI
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class RegisterRetrofitWork(private val context: Context, private val userInfo: SignUpDTO, private val activity: RegsisterActivity) {
    fun work() {
        val service = RetrofitAPI.emgMedService2
        service.addUserByEnqueue(userInfo)
            .enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            val responseString = responseBody.string()
                            when (responseString) {
                                "0" -> {
                                    Toast.makeText(context, "비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show()
                                    Log.d("회원가입 실패", "비밀번호를 확인하세요.")
                                }
                                "1" -> {
                                    Toast.makeText(context, "중복된 아이디 입니다.", Toast.LENGTH_SHORT).show()
                                    Log.d("회원가입 실패", "중복된 아이디 입니다.")
                                }
                                "9" -> {
                                    activity.finish()
                                    Toast.makeText(context, "회원가입 성공!!", Toast.LENGTH_SHORT).show()
                                    Log.d("회원가입 성공", "회원가입 성공!!")
                                }
                                else -> {
                                    Toast.makeText(context, "오류 발생!!", Toast.LENGTH_SHORT).show()
                                    // 다른 응답에 대한 처리
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(context, "인터넷 연결 확인!!", Toast.LENGTH_SHORT).show()
                    Log.d("회원가입 실패", t.message.toString())
                    Log.i("json", userInfo.toString())
                }
            })
    }
}
