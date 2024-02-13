package com.example.plzproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.plzproject.databinding.ActivityLoginBinding
import com.example.plzproject.signin.SignInRequestBody
import com.example.plzproject.signin.LoginRetrofitWork
import java.net.CookieManager


class LoginActivity : AppCompatActivity(), View.OnClickListener{
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    private val registerpage = View.OnClickListener {
        val intent = Intent(this, RegsisterActivity::class.java)
        startActivity(intent)
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Log.i("et_id", binding.etId.text.toString())

        binding.btnLogin.setOnClickListener{
            val userData = SignInRequestBody(
                binding.etId.text.toString(),
                binding.etPw.text.toString()
            )

            val loginRetrofitWork = LoginRetrofitWork(this, userData, this)

//            loginRetrofitWork.setCookieListener { newCookie ->
//                if(!newCookie.isNullOrBlank()){
//                    val cookieManager = CookieManager(this)
//                    cookieManager.saveCookie(newCookie)
//                }
//                else
//                    Toast.makeText(this, "쿠키를 받지 못했습니다...", Toast.LENGTH_SHORT).show()
//                    Log.e("setCookieListen", "쿠키를 저장하지 못했습니다.")
//            }

            loginRetrofitWork.work()
            Log.i("userData", userData.toString())
            Log.i("et_id2", binding.etId?.text.toString())
            Log.i("et_pw", binding.etPw.text.toString())

        }

        binding.btnRegister.setOnClickListener(registerpage)

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}

