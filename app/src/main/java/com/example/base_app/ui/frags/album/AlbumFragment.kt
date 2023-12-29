package com.example.base_app.ui.frags.album

import android.os.Bundle
import com.example.base_app.R
import com.example.base_app.databinding.FragmentAlbumBinding
import com.example.base_app.ui.base.BaseFragment

class AlbumFragment: BaseFragment<FragmentAlbumBinding, AlbumViewModel>(){

    companion object{
        fun newInstance() = AlbumFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

    override fun layoutRes(): Int = R.layout.fragment_album

    override fun viewModelClass(): Class<AlbumViewModel> = AlbumViewModel::class.java

    override fun initView() {
    }


}