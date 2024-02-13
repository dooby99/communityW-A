package com.example.plzproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.plzproject.databinding.ActivityRegsisterBinding
import com.example.plzproject.signup.RegisterRetrofitWork
import com.example.plzproject.signup.SignUpDTO

class RegsisterActivity : AppCompatActivity(), View.OnClickListener{
    private val binding by lazy { ActivityRegsisterBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener{
            val userData2 = SignUpDTO(
                binding.etRId.text.toString(),
                binding.etRPw.text.toString(),
                binding.etRPwR.text.toString()
            )
            val registerRetrofitWork = RegisterRetrofitWork(this, userData2, this)
            registerRetrofitWork.work()
            Log.i("et_id2", binding.etRId.text.toString())
            Log.i("et_pw", binding.etRPw.text.toString())
        }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}