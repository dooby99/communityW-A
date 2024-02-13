package com.example.plzproject.boardWrite

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.plzproject.BoardActivity
import com.example.plzproject.WriteActivity
import com.example.plzproject.signin.RetrofitAPI
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.parse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File

class WriteRetrofitWork(private val context: Context, private val data: WriteDTO, private val activity:WriteActivity) {
    fun work() {

        val service = RetrofitAPI.emgMedService

        // JSON 파트 생성
        val jsonPart =
            RequestBody.create("application/json".toMediaTypeOrNull(), Gson().toJson(data))

        // 파일 파트 생성 (예시)
        val fileParts = mutableListOf<MultipartBody.Part>()

        data.imageUri?.let { uri ->
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
            } else {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            }

            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)

            val byteArray = byteArrayOutputStream.toByteArray()

            val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), byteArray)

            val filePart = MultipartBody.Part.createFormData("file", "image.jpg", requestFile)
            fileParts.add(filePart)
        }

        service.writePost(jsonPart, fileParts)
            .enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        when (response.body()?.string()) {
                            "9" -> {
                                Toast.makeText(context, "게시글 작성 성공", Toast.LENGTH_LONG).show()
                                val intent = Intent(context, BoardActivity::class.java)
                                context.startActivity(intent)
                                activity.finish()
                            }

                            "0" -> Toast.makeText(context, "게시글 작성 실패", Toast.LENGTH_SHORT).show()
                            else -> Toast.makeText(context, "오류 발생!!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(context, "통신 오류입니다.", Toast.LENGTH_SHORT).show()
                }
            })
    }
}