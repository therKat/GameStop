package com.example.base_app.ui.base

import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import com.example.base_app.data.preferences.SharedPreferences
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    @Inject
    protected lateinit var sharedPreferences: SharedPreferences

    /**
     *  Image
     * */
    val uriImage: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    val imageProjection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.HEIGHT,
            MediaStore.Images.Media.WIDTH,
        )
    } else {
        arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.HEIGHT,
            MediaStore.Images.Media.WIDTH,
        )
    }

    /**
     * Video
     * */

    val uriVideo: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

    val videoProjection = arrayOf(
        MediaStore.Video.Media._ID,
        MediaStore.Video.Media.DISPLAY_NAME,
        MediaStore.Video.Media.SIZE,
        MediaStore.Video.Media.DATA,
        MediaStore.Video.Media.HEIGHT,
        MediaStore.Video.Media.WIDTH,
        MediaStore.Video.Media.DURATION,
    )

    /** Album image*/

    protected val albumProjection = arrayOf(
        MediaStore.Images.Media.BUCKET_ID,
        MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
        MediaStore.Images.Media.DATA,
    )

}