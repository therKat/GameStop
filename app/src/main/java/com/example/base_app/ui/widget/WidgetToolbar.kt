package com.example.base_app.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.example.base_app.databinding.WidgetToolbarBinding
import com.example.base_app.extension.beGone
import com.example.base_app.extension.beVisible
import com.example.base_app.extension.onClickListener

class WidgetToolbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private var binding: WidgetToolbarBinding
    private var onEventToolbar: IOnEventToolbar? = null

    init {
        binding = WidgetToolbarBinding.inflate(LayoutInflater.from(context), this, true)
        binding.btnBack.onClickListener {
            onEventToolbar?.onBackListener()
        }
        binding.btnDone.onClickListener {
            onEventToolbar?.onRightIconClick(it)
        }
    }

    fun setEvent(onEventToolbar: IOnEventToolbar){
        this.onEventToolbar = onEventToolbar
    }

    fun setTitle(resId: Int){
        binding.tvTitle.text = resources.getString(resId)
    }
    fun setTitle(string: String){
        binding.tvTitle.text = string
    }

    fun setVisibilityDone(boolean: Boolean){
        if (boolean) binding.btnDone.beVisible() else binding.btnDone.beGone()
    }

    fun setVisibilityBack(boolean: Boolean){
        if (boolean) binding.btnBack.beVisible() else binding.btnBack.beGone()
    }

    fun setDoneResource(resId: Int){
        binding.imgDone.setImageResource(resId)
    }

    interface IOnEventToolbar{
        fun onBackListener()
        fun onRightIconClick(v: View)
    }

}