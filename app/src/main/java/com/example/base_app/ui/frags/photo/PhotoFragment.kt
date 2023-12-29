package com.example.base_app.ui.frags.photo

import android.os.Bundle
import com.example.base_app.R
import com.example.base_app.databinding.FragmentPhotoBinding
import com.example.base_app.ui.base.BaseFragment

class PhotoFragment : BaseFragment<FragmentPhotoBinding, PhotoViewModel>(){


    companion object {
        fun newInstance() = PhotoFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

    override fun layoutRes(): Int = R.layout.fragment_photo

    override fun viewModelClass(): Class<PhotoViewModel> = PhotoViewModel::class.java

    override fun initView() {

    }

}