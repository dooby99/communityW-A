package com.example.plzproject.boardWrite

import android.net.Uri


data class WriteDTO(
    val title: String,
    val content: String,
    val subject: String,
    val imageUri: Uri? = null

)
