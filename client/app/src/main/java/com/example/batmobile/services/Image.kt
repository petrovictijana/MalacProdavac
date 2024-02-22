package com.example.batmobile.services

import android.content.Context
import android.net.Uri
import android.util.Base64
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

class Image {
    companion object{
        fun uriToBase64(context: Context, uri: Uri): String {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val bufferedInputStream = BufferedInputStream(inputStream)
            val outputStream = ByteArrayOutputStream()

            bufferedInputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            val imageByteArray = outputStream.toByteArray()
            return Base64.encodeToString(imageByteArray, Base64.DEFAULT)
        }
    }
}