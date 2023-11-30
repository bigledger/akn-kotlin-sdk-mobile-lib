package com.akaun.kt.mobile.utils


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream

suspend fun responseBodyToUri(context: Context, responseBody: ResponseBody, attachmentGuid: String): Uri? {
    return withContext(Dispatchers.IO) {
        try {
            // Convert ResponseBody to Bitmap
            val inputStream = responseBody.byteStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()

            // Generate a unique filename based on the attachment GUID
            val filename = "attachment_$attachmentGuid.jpg"

            // Save Bitmap as a temporary file with the unique filename
            val tempFile = File(context.cacheDir, filename)
            val outputStream = FileOutputStream(tempFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()

            // Get Uri for the temporary file
            Uri.fromFile(tempFile)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

