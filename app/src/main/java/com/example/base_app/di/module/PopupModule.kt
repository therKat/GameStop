package com.example.base_app.di.module


import com.example.base_app.ui.popup.rate.PopupRate
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PopupModule {
    @ContributesAndroidInjector
    abstract fun bindPopupRate(): PopupRate

}