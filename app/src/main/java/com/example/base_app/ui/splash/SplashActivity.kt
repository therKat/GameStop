package com.example.base_app.ui.splash

import com.example.base_app.R
import com.example.base_app.databinding.ActivitySplashBinding
import com.example.base_app.extension.nextActivity
import com.example.base_app.extension.updateResource
import com.example.base_app.ui.base.BaseActivity
import com.example.base_app.ui.intro.IntroductionActivity
import com.example.base_app.ui.language.LanguageActivity
import com.example.base_app.ui.main.MainActivity
import com.example.base_app.ui.permission.IntroViewModel
import com.example.base_app.ui.permission.PermissionActivity

class SplashActivity: BaseActivity<ActivitySplashBinding, IntroViewModel>(){

    override fun layoutRes(): Int = R.layout.activity_splash

    override fun viewModelClass(): Class<IntroViewModel> = IntroViewModel::class.java

    override fun initView() {
        updateResource(sharedPreferences.langCurrent)
        binding.imgLaunch.postDelayed({
            if (sharedPreferences.isFirstOpen){
                nextActivity(MainActivity::class.java)
                finish()
            }else{
                nextActivity(MainActivity::class.java)
                finish()
            }
        }, 3000)
    }

    private fun moveActivity() {
        when(sharedPreferences.screenCurrent){
            "lang" -> {
                nextActivity(LanguageActivity::class.java)
            }
            "introduction" -> {
                nextActivity(IntroductionActivity::class.java)
            }
            "permission" -> {
                nextActivity(PermissionActivity::class.java)
            }
        }
    }

}