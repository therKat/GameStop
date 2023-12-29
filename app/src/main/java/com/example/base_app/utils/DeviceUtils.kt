package com.example.base_app.utils

import android.Manifest
import android.os.Build
import android.os.Environment
import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import java.io.File

val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.d("TAGGG", "Error: ${throwable.message}")
}

val storagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
    arrayOf(Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO)
else
    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

val pictureDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

val privatePath = Environment.getExternalStorageDirectory().path + File.separator + ".hiddenAlbums" + File.separator + ".private"

val publicPath = Environment.getExternalStorageDirectory().path + File.separator + "Recent"

val imageExtension = listOf("jpg", "jpeg", "png", "gif", "bmp")

val videoExtension = listOf("mp4", "3gp", "avi", "mkv")