package com.example.base_app.ui.permission

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import com.example.base_app.R
import com.example.base_app.databinding.ActivityPermissionBinding
import com.example.base_app.extension.beGone
import com.example.base_app.extension.beVisible
import com.example.base_app.extension.hasPermission
import com.example.base_app.extension.nextActivity
import com.example.base_app.extension.onClickListener
import com.example.base_app.ui.base.BaseActivity
import com.example.base_app.ui.main.MainActivity
import com.example.base_app.utils.constants.Constants
import com.example.base_app.utils.storagePermission

class PermissionActivity: BaseActivity<ActivityPermissionBinding, IntroViewModel>() {
    private var type = -1
    private var numCountDeniedPermission = 0

    private val launchPer = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions ->
        var granted = 0
        permissions.forEach { (k, v) ->
            if (!v){
                granted++
            }
        }

        if (granted == 0){
            handlePermissionGranted()
        }else{
            numCountDeniedPermission++
            handlePermissionNotGranted()
        }
    }

    private fun handlePermissionGranted() {
        when(type){
            Constants.TYPE_FIRST_PERMISSION -> {
                binding.switch1.isChecked = true
                binding.switch1.isEnabled = false
            }
        }
    }

    private fun handlePermissionNotGranted() {
        when(type){
            Constants.TYPE_FIRST_PERMISSION -> {
                binding.switch1.isChecked = false
                binding.switch1.isEnabled = true
            }
        }
    }

    override fun layoutRes(): Int = R.layout.activity_permission

    override fun viewModelClass(): Class<IntroViewModel> = IntroViewModel::class.java

    override fun initView() {
        sharedPreferences.screenCurrent = "permission"
        setDefaultChecked()
        initEvent()
    }

    private fun initEvent() {
        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                    binding.switch1.isChecked = Environment.isExternalStorageManager()
                }else{
                    binding.switch1.isChecked = hasPermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                }
                if (numCountDeniedPermission >= 2){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        startActivity(Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, Uri.parse("package:${packageName}")))
                    }else{
                        startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName")))
                    }
                }else{
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        startActivity(Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, Uri.parse("package:${packageName}")))
                    }else{
                        launchPer.launch(storagePermission)
                    }
                }
            }
        }

        binding.btnGo.onClickListener {
            if (binding.switch1.isChecked){
                nextActivity(MainActivity::class.java)
                finish()
            }
        }
    }

    private fun setDefaultChecked() {
        binding.switch1.isChecked = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        }else{
            hasPermission(storagePermission)
        }
        binding.switch1.isEnabled = !binding.switch1.isChecked
        if (binding.switch1.isChecked) binding.btnGo.beVisible() else binding.btnGo.beGone()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (binding.switch1.isChecked){
            binding.btnGo.beVisible()
        }else{
            binding.btnGo.beGone()
        }
    }

    override fun onResume() {
        super.onResume()
        setDefaultChecked()
    }

}