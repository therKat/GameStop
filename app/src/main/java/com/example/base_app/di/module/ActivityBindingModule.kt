package com.example.base_app.di.module

import com.example.base_app.ui.intro.IntroductionActivity
import com.example.base_app.ui.language.LanguageActivity
import com.example.base_app.ui.main.MainActivity
import com.example.base_app.ui.permission.PermissionActivity
import com.example.base_app.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun bindLanguageActivity(): LanguageActivity

    @ContributesAndroidInjector
    abstract fun bindIntroductionActivity(): IntroductionActivity

    @ContributesAndroidInjector
    abstract fun bindPermissionActivity(): PermissionActivity

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

}