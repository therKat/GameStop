package com.example.base_app.ui.frags.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.example.base_app.R
import com.example.base_app.databinding.FragmentSettingBinding
import com.example.base_app.extension.beGone
import com.example.base_app.extension.nextActivity
import com.example.base_app.extension.onClickListener
import com.example.base_app.ui.base.BaseFragment
import com.example.base_app.ui.language.LanguageActivity
import com.example.base_app.ui.popup.rate.PopupRate
import com.example.base_app.utils.constants.Constants

class SettingFragment: BaseFragment<FragmentSettingBinding, SettingViewModel>(),
    PopupRate.IOnEventPopupRate {

    private lateinit var popupRate: PopupRate

    companion object{

        fun newInstance() = SettingFragment().apply {
            arguments = Bundle().apply {
                
            }
        }

    }

    override fun layoutRes(): Int = R.layout.fragment_setting

    override fun viewModelClass(): Class<SettingViewModel> = SettingViewModel::class.java

    override fun initView() {
        popupRate = PopupRate.newInstance()
        popupRate.applyEvent(this)
        initToolbar()
        initEvent()
    }

    private fun initEvent() {
        if (sharedPreferences.numRate == 5) binding.rateSetting.beGone()
        binding.btnLanguage.onClickListener {
            requireContext().nextActivity(LanguageActivity::class.java, Bundle().apply {
                putInt(Constants.EXTRA_DATA_TO_LANGUAGE, 1)
            })
        }
        binding.rateSetting.onClickListener {
            if (sharedPreferences.numRate < 5){
                popupRate.show(childFragmentManager, "")
            }
        }
        binding.privacySetting.onClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.POLICY_PATH)))
        }

        if (sharedPreferences.typeOfLock < 0){
            binding.btnForgotPassword.beGone()
        }

    }

    private fun initToolbar() {
        binding.mToolbar.setTitle(R.string.setting)
        binding.mToolbar.setVisibilityBack(false)
        binding.mToolbar.setVisibilityDone(false)
    }

    override fun onClickRate() {
        if (sharedPreferences.numRate == 5){
            binding.rateSetting.beGone()
        }
    }

    override fun onResume() {
        super.onResume()
        if (sharedPreferences.typeOfLock < 0){
            binding.btnForgotPassword.beGone()
        }
    }

}