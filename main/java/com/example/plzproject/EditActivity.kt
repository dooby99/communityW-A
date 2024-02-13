package com.example.plzproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import com.example.plzproject.boardDetail.Board
import com.example.plzproject.boardDetail.EditRetrofitWork
import com.example.plzproject.databinding.ActivityEditBinding
import org.jsoup.Jsoup

class EditActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy {
        ActivityEditBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        setContentView(R.layout.activity_edit)

        val htmlData = intent.getStringExtra("htmlData") ?: ""

        // Parse the HTML string with Jsoup
        val doc = Jsoup.parse(htmlData)

        // Extract the desired values
        val titleText = doc.select("input[name=title]").attr("value")
        val contentText = doc.select("textarea[name=content]").text()
        val actionValue = doc.select("form").attr("action")
        Log.i("title", titleText.toString())
        Log.i("content", contentText.toString())

        binding.etTitle2.setText(titleText)
        binding.etContent2.setText(contentText)

        binding.CheckButton.setOnClickListener {
            val selectedRadioButtonId = binding.radioGroup.checkedRadioButtonId
            val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
            val selectedRadioText = selectedRadioButton.text.toString()

            val data = Board(
                binding.etTitle2.text.toString(),
                binding.etContent2.text.toString(),
                selectedRadioText
            )

            val lastPart = actionValue.substringAfterLast("/")
            val edit = EditRetrofitWork(this, DetailViewActivity())
            edit.editPost(lastPart, data)
            Log.i("linkId", lastPart)
            Log.i("Body Data", data.content)
            Log.i("Body Data", data.subject)
            Log.i("Body Data", data.title)

        }


    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}

