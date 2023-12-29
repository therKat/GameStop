package com.example.base_app.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.base_app.di.util.ViewModelFactory
import com.example.base_app.di.util.ViewModelKey
import com.example.base_app.ui.frags.album.AlbumViewModel
import com.example.base_app.ui.frags.home.HomeViewModel
import com.example.base_app.ui.frags.photo.PhotoViewModel
import com.example.base_app.ui.frags.setting.SettingViewModel
import com.example.base_app.ui.main.MainViewModel
import com.example.base_app.ui.permission.IntroViewModel
import com.example.base_app.ui.popup.viewmodel.PopupGeneralViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IntroViewModel::class)
    abstract fun bindIntroViewModel(introViewModel: IntroViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PhotoViewModel::class)
    abstract fun bindPhotoViewModel(photoViewModel: PhotoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AlbumViewModel::class)
    abstract fun bindAlbumViewModel(albumViewModel: AlbumViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel::class)
    abstract fun bindSettingViewModel(settingViewModel: SettingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PopupGeneralViewModel::class)
    abstract fun bindPopupGeneralViewModel(popupGeneralViewModel: PopupGeneralViewModel): ViewModel

}