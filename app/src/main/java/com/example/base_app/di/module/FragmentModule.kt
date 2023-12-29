package com.example.base_app.di.module


import com.example.base_app.ui.frags.album.AlbumFragment
import com.example.base_app.ui.frags.home.HomeFragment
import com.example.base_app.ui.frags.photo.PhotoFragment
import com.example.base_app.ui.frags.setting.SettingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun bindPhotoFragment(): PhotoFragment

    @ContributesAndroidInjector
    abstract fun bindAlbumFragment(): AlbumFragment

    @ContributesAndroidInjector
    abstract fun bindSettingFragment(): SettingFragment

}