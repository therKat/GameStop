package com.example.base_app.utils.constants

import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.webkit.MimeTypeMap
import com.example.base_app.R
import com.example.base_app.utils.imageExtension
import com.example.base_app.utils.pictureDir
import com.example.base_app.utils.videoExtension
import java.io.File
import java.text.DecimalFormat
import java.util.regex.Pattern
import kotlin.jvm.internal.Intrinsics

object FileUtils {

    fun getAlbumPath(context: Context): String{
        return pictureDir.path + File.separator + context.resources.getString(R.string.app_name)
    }

    fun byteToMB(context: Context, size: Long?): String? {
        if (size != null){
            val format = DecimalFormat("0.00")
            val ff = size.toFloat()
            if (ff < 1048576.0f) {
                return Intrinsics.stringPlus(format.format((ff / 1024.0f).toDouble()), "KB")
            }
            if (ff < 1.07374182E9f) {
                return Intrinsics.stringPlus(format.format((ff / 1048576.0f).toDouble()), "MB")
            }
            return if (ff < 1.09951163E12f) Intrinsics.stringPlus(format.format((ff / 1.07374182E9f).toDouble()), "GB") else ""
        }else{
            return context.getString(R.string.unable_to_calculate_the_size_of_this_file)
        }
    }

    fun isImageExtension(extension: String): Boolean{
        return imageExtension.contains(extension)
    }

    fun isVideoExtension(extension: String): Boolean{
        return videoExtension.contains(extension)
    }

    fun getVideoDuration(videoPath: String): Long{
        return try {
            val receiver = MediaMetadataRetriever()
            receiver.setDataSource(videoPath)
            val duration = receiver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            duration?.toLongOrNull() ?: 0L
        }catch (ex: Exception){
            ex.printStackTrace()
            0L
        }
    }

    fun getResolutionImage(imagePath: String?): Pair<Int, Int>{
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(imagePath, this)
        }
        return Pair(options.outWidth, options.outHeight)
    }

    fun getResolutionVideo(videoPath: String?): Pair<Int, Int>?{
        val retriever = MediaMetadataRetriever()
        return try {
            retriever.setDataSource(videoPath)
            val width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)?.toInt() ?: -1
            val height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)?.toInt() ?: -1
            if (width > 0 && height > 0) Pair(width, height) else null
        }catch (ex: Exception){
            ex.printStackTrace()
            null
        }finally {
            retriever.release()
        }
    }

    fun isVideo(path: String): Boolean{
        val extension = MimeTypeMap.getFileExtensionFromUrl(path)
        if (extension == ""){
            val mimeType = path.substring(path.lastIndexOf(".") + 1, path.length)
            return  FileUtils.isVideoExtension(mimeType)
        }
        return FileUtils.isVideoExtension(extension)
    }

    fun getSpecialChar(strSpecial: String?): Boolean {
        val pattern = Pattern.compile("[^A-Za-z0-9_ ]")
        Intrinsics.checkNotNullExpressionValue(pattern, "compile(\"[^A-Za-z0-9_ ]\")")
        val matchers = pattern.matcher(strSpecial)
        Intrinsics.checkNotNullExpressionValue(matchers, "p.matcher(s)")
        return matchers.find()
    }
}