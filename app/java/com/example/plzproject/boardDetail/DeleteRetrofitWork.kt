package com.example.plzproject.boardDetail

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.plzproject.BoardActivity
import com.example.plzproject.DetailViewActivity
import com.example.plzproject.signin.RetrofitAPI
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.net.URLDecoder

class DeleteRetrofitWork (private val context: Context, private val activity: DetailViewActivity) {
    fun work(linkId: String) {

        val service = RetrofitAPI.emgMedService

        // Link decode and get ID
//        val decodedLinkHref = URLDecoder.decode(linkHref, "UTF-8")
//        val linkId = decodedLinkHref.split("/").last()
        service.deleteBoard(linkId)
            .enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        when (val responseBody = response.body()?.string()) {
                            null -> Toast.makeText(context, "데이터가 없습니다.", Toast.LENGTH_SHORT).show()
                            else -> {
                                // 데이터를 받아왔을 때 실행할 액션
                                val intent = Intent(context, BoardActivity::class.java)
                                context.startActivity(intent)
                                activity.finish()

                                Log.d("Network request", "Success! Response data:$responseBody")
                            }
                        }
                    } else {
                        Log.e("Network request", "Failed! Error message:${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(context, "통신 오류입니다.", Toast.LENGTH_SHORT).show()
                }
            })
    }
}