package com.example.base_app.ui.language

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.base_app.R
import com.example.base_app.data.model.Language
import com.example.base_app.databinding.ActivityLanguageBinding
import com.example.base_app.extension.languages
import com.example.base_app.extension.nextActivity
import com.example.base_app.extension.nextActivityClearTask
import com.example.base_app.extension.updateResource
import com.example.base_app.ui.base.BaseActivity
import com.example.base_app.ui.intro.IntroductionActivity
import com.example.base_app.ui.language.adapter.LanguageAdapter
import com.example.base_app.ui.main.MainActivity
import com.example.base_app.ui.permission.IntroViewModel
import com.example.base_app.ui.widget.WidgetToolbar
import com.example.base_app.utils.constants.Constants

class LanguageActivity: BaseActivity<ActivityLanguageBinding, IntroViewModel>(),
    WidgetToolbar.IOnEventToolbar, LanguageAdapter.IOnEventLanguageListener {

    private var langCode = "en"

    private lateinit var mAdapter: LanguageAdapter

    private var fromPos = -1

    override fun layoutRes(): Int = R.layout.activity_language

    override fun viewModelClass(): Class<IntroViewModel> = IntroViewModel::class.java

    override fun initView() {
        fromPos = intent.extras?.getInt(Constants.EXTRA_DATA_TO_LANGUAGE, -1) ?: -1
        langCode = sharedPreferences.langCurrent
        sharedPreferences.screenCurrent = "lang"
        initToolbar()
        initRcvLanguage()
    }

    private fun initRcvLanguage() {
        val languages = languages()
        mAdapter = LanguageAdapter()
        binding.rvLanguage.apply {
            layoutManager = LinearLayoutManager(this@LanguageActivity)
            adapter = mAdapter
        }
        mAdapter.applyData(languages)
        mAdapter.applyEvent(this)
        val language = languages.find { it.code == sharedPreferences.langCurrent }
        if (language != null){
            val pos = languages.indexOf(language)
            if (pos != -1){
                mAdapter.setPosSelected(pos)
            }
        }
    }

    private fun initToolbar() {
        binding.mToolbar.setTitle(R.string.language)
        binding.mToolbar.setEvent(this)
        if (fromPos != -1){
            binding.mToolbar.setVisibilityBack(true)
        }else{
            binding.mToolbar.setVisibilityBack(false)
        }
    }

    override fun onBackListener() {
        finish()
    }

    override fun onRightIconClick(v: View) {
        sharedPreferences.langCurrent = langCode
        updateResource(sharedPreferences.langCurrent)
        if (fromPos != -1){
            nextActivityClearTask(MainActivity::class.java)
        }else{
            nextActivity(IntroductionActivity::class.java)
        }
        finish()
    }

    override fun onClickLanguage(language: Language) {
        langCode = language.code
    }

}