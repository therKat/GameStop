package com.example.base_app.ui.main

import com.example.base_app.R
import com.example.base_app.databinding.ActivityMainBinding
import com.example.base_app.extension.beGone
import com.example.base_app.extension.beVisible
import com.example.base_app.ui.base.BaseActivity
import com.example.base_app.ui.frags.album.AlbumFragment
import com.example.base_app.ui.frags.home.HomeFragment
import com.example.base_app.ui.frags.photo.PhotoFragment
import com.example.base_app.ui.frags.setting.SettingFragment

class MainActivity: BaseActivity<ActivityMainBinding, MainViewModel>(){

    override fun layoutRes(): Int = R.layout.activity_main

    override fun viewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun initView() {
        sharedPreferences.isFirstOpen = false
        replace(HomeFragment.newInstance())
        binding.bottomNav.setItemSelected(R.id.menu_home, true)
        initEvent()
    }

    private fun initEvent() {
        binding.bottomNav.setOnItemSelectedListener {
            when(it){
                R.id.menu_home -> {
                    replace(HomeFragment.newInstance())
                }
                R.id.menu_photo -> {
                    replace(PhotoFragment.newInstance())
                }
                R.id.menu_album -> {
                    replace(AlbumFragment.newInstance())
                }
                R.id.menu_setting -> {
                    replace(SettingFragment.newInstance())
                }
            }
        }
    }

    fun hideBottomNav(bool: Boolean){
        if (bool) binding.bottomNav.beVisible() else binding.bottomNav.beGone()
    }

    fun moveToFrag(id: Int){
        binding.bottomNav.setItemSelected(id, true)
    }

}