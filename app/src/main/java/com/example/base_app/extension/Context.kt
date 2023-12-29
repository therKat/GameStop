package com.example.base_app.extension

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.base_app.R
import com.example.base_app.data.model.IntroModel
import com.example.base_app.data.model.Language
import com.example.base_app.data.preferences.SharedPreferences
import com.example.base_app.utils.constants.FileUtils
import com.example.base_app.utils.pictureDir
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.util.Locale

fun Context.toast(int: Int, length: Int = Toast.LENGTH_SHORT){
    toast(getString(int), length)
}

fun Context.toast(msg: String, length: Int = Toast.LENGTH_SHORT){
    CoroutineScope(Dispatchers.Main).launch {
        doToast(this@toast, msg, length)
    }
}

private fun doToast(context: Context, msg: String, length: Int){
    if (context is Activity){
        if (!context.isDestroyed && !context.isFinishing){
            Toast.makeText(context, msg, length).show()
        }
    }else{
        Toast.makeText(context, msg, length).show()
    }
}

val permissionImageStorage =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        Manifest.permission.READ_MEDIA_IMAGES
    else
        Manifest.permission.WRITE_EXTERNAL_STORAGE


val Context.hasPermissionImageStorage: Boolean
    get() = ContextCompat.checkSelfPermission(this, permissionImageStorage) == PackageManager.PERMISSION_GRANTED

fun Context.setLocale() {
    val language = SharedPreferences(this).langCurrent
    if (language == "") {
        val config = Configuration()
        val locale = Locale.getDefault()
        Locale.setDefault(locale)
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
    } else {
        changeLang(language)
    }
}

fun Context.updateResource(langCode: String){
    val locale = Locale(langCode)
    Locale.setDefault(locale)
    val resource = this.resources
    val configuration = resource.configuration
    configuration.setLocale(locale)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
        resource.updateConfiguration(configuration, resource.displayMetrics)
    }else{
        this.createConfigurationContext(configuration)
    }
}

fun Context.changeLang(lang: String) {
    if (lang == ""){
        val sharePref = SharedPreferences(this)
        if (lang.equals("", ignoreCase = true)) return
        sharePref.langCurrent = lang
        Locale(lang).let {
            Locale.setDefault(it)
            val config = Configuration()
            config.locale = it
            resources.updateConfiguration(config, resources.displayMetrics)
        }
    }
}

fun Context.languages() = arrayListOf(
    Language(resources.getString(R.string.english), "en", R.drawable.eng_flag),
    Language(resources.getString(R.string.spanish), "es", R.drawable.spain_flag),
    Language(resources.getString(R.string.french), "fr", R.drawable.france_flag),
    Language(resources.getString(R.string.hindi), "hi", R.drawable.india_flag),
    Language(resources.getString(R.string.portuguese), "pt", R.drawable.portugal_flag),
)

fun Context.itemsIntro() = arrayListOf(
    IntroModel(resources.getString(R.string.title_intro_first), resources.getString(R.string.content_intro_first), R.drawable.intro_1),
    IntroModel(resources.getString(R.string.title_intro_second), resources.getString(R.string.content_intro_second), R.drawable.intro_2),
    IntroModel(resources.getString(R.string.title_intro_third), resources.getString(R.string.content_intro_third), R.drawable.intro_3),
)

fun Context.nextActivity(activity: Class<*>, bundle: Bundle? = null){
    this.startActivity(Intent(this, activity).apply {
        bundle?.let {
            putExtras(bundle)
        }
    })
}

fun Context.nextActivityClearTask(activity: Class<*>, bundle: Bundle? = null){
    this.startActivity(Intent(this, activity).apply {
        bundle?.let {
            putExtras(bundle)
        }
        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
    })
}

fun Context.hasPermission(permissions: Array<String>): Boolean{
    var granted = 0
    permissions.forEach { per ->
        if (ContextCompat.checkSelfPermission(this, per) == PackageManager.PERMISSION_DENIED){
            granted++
        }
    }
    return granted == 0
}

fun Context.createAlbum(albumName: String, onSuccess:() -> Unit, onFailed:()->Unit){
    val storageDir = FileUtils.getAlbumPath(this)
    val albumDir = File(storageDir, albumName)
    if (!albumDir.exists()){
        if (albumDir.mkdirs()){
            scanMedia(albumDir.path)
            onSuccess()
        }else{
            onFailed()
        }
    }else{
        toast("Album already exist: $albumName")
    }
}

fun Context.copyPhotoToAlbum(directory: String, photoPath: String, albumName: String){
    val albumDir = File(directory, albumName)
    if (albumDir.exists()){
        val originalFile = File(photoPath)
        val newFile = File(albumDir, originalFile.name)
        try {
            val bool = originalFile.renameTo(newFile)
            if (bool){
                scanMedia(originalFile.path)
                scanMedia(newFile.path)
            }else{
                toast(R.string.something_went_wrong)
            }
        }catch (ex: Exception){
            ex.printStackTrace()
            toast(resources.getString(R.string.failed_to_move_photo))
        }
    }else{
        toast(resources.getString(R.string.cannot_find_album))
    }
}

fun Context.removeMediaToPicture(photoPath: String){
    val originalFile = File(photoPath)
    val newFile = File(pictureDir, originalFile.name)
    try {
        originalFile.renameTo(newFile)
        scanMedia(originalFile.path)
        scanMedia(newFile.path)
    }catch (ex: Exception){
        ex.printStackTrace()
        toast(resources.getString(R.string.failed_to_move_photo))
    }
}

fun Context.getIntentSend(uri: String, mType: String): Intent {
    val fileUri = FileProvider.getUriForFile(this, "${packageName}.provider", File(uri))
    return Intent(Intent.ACTION_SEND).apply {
        type = mType
        putExtra(Intent.EXTRA_STREAM, fileUri)
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
}


fun Context.deleteAlbum(albumPath: String?, onSuccess: () -> Unit, onFailed: () -> Unit){
    val file = albumPath?.let { File(it) }
    if (file?.exists() == true){
        val bool = file.deleteRecursively()
        if (bool){
            scanMedia(file.path)
            Handler(Looper.getMainLooper()).postDelayed({
                onSuccess()
            },250)
        }else{
            onFailed()
//            toast(R.string.something_went_wrong)
        }
    }else{
        onFailed()
//        toast(R.string.file_not_found)
    }
}

fun Context.scanMedia(path: String) {
    val file = File(path)
    val uri = Uri.fromFile(file)
    val scanFileIntent = Intent(
        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri
    )
    sendBroadcast(scanFileIntent)
}