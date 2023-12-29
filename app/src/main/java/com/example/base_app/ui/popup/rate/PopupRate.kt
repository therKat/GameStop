package com.example.base_app.ui.popup.rate

import android.os.Bundle
import com.example.base_app.R
import com.example.base_app.databinding.PopupRateUsBinding
import com.example.base_app.extension.onClickListener
import com.example.base_app.extension.toast
import com.example.base_app.ui.base.BaseDialog
import com.example.base_app.ui.popup.viewmodel.PopupGeneralViewModel

class PopupRate: BaseDialog<PopupRateUsBinding, PopupGeneralViewModel>() {

    companion object{
        fun newInstance() = PopupRate().apply {
            arguments = Bundle().apply {

            }
        }
    }

    private lateinit var popupRateEvent: IOnEventPopupRate

    fun applyEvent(popupRateEvent: IOnEventPopupRate){
        this.popupRateEvent = popupRateEvent
    }

    override fun layoutRes(): Int = R.layout.popup_rate_us

    override fun viewModelClass(): Class<PopupGeneralViewModel> = PopupGeneralViewModel::class.java

    override fun initView() {
        setLayout(8)
        binding.ratingBar.setOnRatingChangeListener { ratingBar, rating, fromUser ->
            when(rating){
                1F -> {
                    setTextWithRate(
                        resources.getString(R.string.oh_no),
                        resources.getString(R.string.please_give_us_some_feedback),
                        R.drawable.rating_1
                    )
                }
                2F -> {
                    setTextWithRate(
                        resources.getString(R.string.oh_no),
                        resources.getString(R.string.please_give_us_some_feedback),
                        R.drawable.rating_2
                    )
                }
                3F -> {
                    setTextWithRate(
                        resources.getString(R.string.oh_no),
                        resources.getString(R.string.please_give_us_some_feedback),
                        R.drawable.rating_3
                    )
                }
                4F -> {
                    setTextWithRate(
                        resources.getString(R.string.we_love_u_too),
                        resources.getString(R.string.thanks_for_your_feedback),
                        R.drawable.rating_4
                    )
                }
                5F -> {
                    setTextWithRate(
                        resources.getString(R.string.we_love_u_too),
                        resources.getString(R.string.thanks_for_your_feedback),
                        R.drawable.rating_5
                    )
                }
                else -> {
                    setTextWithRate(
                        resources.getString(R.string.do_you_like_the_app),
                        resources.getString(R.string.let_us_know_your_experience),
                        R.drawable.rating_default
                    )
                }
            }
        }

        initEvent()
    }

    private fun initEvent() {
        binding.ratingBar.rating = 5f
        binding.btnRate.onClickListener {
            if (binding.ratingBar.rating == 0f){
                requireContext().toast(resources.getString(R.string.please_feedback))
            }else if (binding.ratingBar.rating <= 4F){
                requireContext().toast(resources.getString(R.string.thank_you))
            }else{
                popupRateEvent.onClickRate()
            }
            requireContext().toast(R.string.thank_you)
            sharedPreferences.numRate = binding.ratingBar.rating.toInt()
            dismiss()
        }
        binding.btnLater.onClickListener {
            dismiss()
        }
    }

    private fun setTextWithRate(title: String, content: String, resId: Int){
        binding.tvTitle.text = title
        binding.tvContent.text = content
        binding.imgIcon.setImageResource(resId)
    }

    interface IOnEventPopupRate{
        fun onClickRate()
    }
}