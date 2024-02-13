package com.example.plzproject

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.plzproject.boardWrite.WriteDTO
import com.example.plzproject.boardWrite.WriteRetrofitWork
import com.example.plzproject.databinding.ActivityWriteBinding
import com.example.plzproject.signin.RetrofitAPI
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class WriteActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy { ActivityWriteBinding.inflate(layoutInflater) }
    private var selectedImageUri: Uri? = null

    private val requestPhoto = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        // 선택한 이미지의 URI를 받아옴
        selectedImageUri = uri
        // 이미지를 표시하거나 다른 작업을 수행할 수 있음
        if (uri != null) {
            // 선택한 이미지를 ImageView에 표시하거나 필요한 작업 수행
            binding.imageView.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        setContentView(binding.root)

        checkLoginStatus()


        binding.ImgBtn.setOnClickListener{
            requestPhoto.launch("image/*")
        }

        binding.CheckButton.setOnClickListener {
            val selectedRadioButtonId = binding.radioGroup.checkedRadioButtonId
            val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
            val selectedRadioText = selectedRadioButton.text.toString()

            val userData = WriteDTO(
                binding.etTitle.text.toString(),
                binding.etContent.text.toString(),
                selectedRadioText,
                selectedImageUri
            )

            val writeRetrofitWork = WriteRetrofitWork(this, userData, this)
            writeRetrofitWork.work()




        }
    }

    private fun checkLoginStatus() {
        val service = RetrofitAPI.emgMedService
        service.checkLoginStatus().enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val responseString = responseBody.string()
                        if (responseString == "board/boardWrite") {
                            // 로그인되었으므로 게시글 작성 화면을 유지합니다.
                            Toast.makeText(this@WriteActivity, "글쓰기 시작.", Toast.LENGTH_SHORT).show()
                            Log.e("WriteInSuccess", "글쓰기 가능")
                        } else {
                            // 로그인되지 않았으므로 로그인 페이지로 이동합니다.
//                            val intent = Intent(this@WriteActivity, BoardActivity::class.java)
//                            startActivity(intent)
//                            finish()
                            Log.i("response", response.toString())
//                            Log.i("responseString", responseString)
                            Log.i("responseBody", responseBody.toString())
                            Toast.makeText(this@WriteActivity, "글쓰기 불가능.", Toast.LENGTH_SHORT).show()
                            Log.e("WriteInFail", "글쓰기 불가능")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // 통신 오류 처리
                Toast.makeText(this@WriteActivity, "통신 오류입니다.", Toast.LENGTH_SHORT).show()
                Log.e("CheckLoginStatus", t.message.toString())
            }
        })
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}