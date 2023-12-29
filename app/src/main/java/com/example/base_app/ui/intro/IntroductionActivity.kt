package com.example.base_app.ui.intro

import com.example.base_app.R
import com.example.base_app.data.model.IntroModel
import com.example.base_app.databinding.ActivityIntroductionBinding
import com.example.base_app.extension.itemsIntro
import com.example.base_app.extension.nextActivity
import com.example.base_app.ui.base.BaseActivity
import com.example.base_app.ui.intro.adapters.IntroductionAdapter
import com.example.base_app.ui.permission.IntroViewModel
import com.example.base_app.ui.permission.PermissionActivity

class IntroductionActivity: BaseActivity<ActivityIntroductionBinding, IntroViewModel>() {

    private lateinit var mAdapter: IntroductionAdapter
    private val items = ArrayList<IntroModel>()

    override fun layoutRes(): Int = R.layout.activity_introduction

    override fun viewModelClass(): Class<IntroViewModel> = IntroViewModel::class.java

    override fun initView() {
        sharedPreferences.screenCurrent = "introduction"
        mAdapter = IntroductionAdapter()
        items.clear()
        items.addAll(itemsIntro())
        mAdapter.applyData(items)
        binding.viewPager.adapter = mAdapter
        binding.dotsIndicator.attachTo(binding.viewPager)
        initEventViewPager()
        initEvent()
    }

    private fun initEvent() {
        binding.buttonStartIntro.setOnClickListener {
            val posCurrent = binding.viewPager.currentItem
            if (posCurrent < items.size - 1){
                binding.viewPager.currentItem = binding.viewPager.currentItem + 1
            }else{
                nextActivity(PermissionActivity::class.java)
                finish()
            }
        }
    }

    private fun initEventViewPager() {
    }

}