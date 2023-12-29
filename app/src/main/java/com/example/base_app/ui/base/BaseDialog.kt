package com.example.base_app.ui.base

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.base_app.R
import com.example.base_app.data.preferences.SharedPreferences
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

abstract class BaseDialog<T: ViewDataBinding, M: BaseViewModel>: DaggerDialogFragment() {

    protected lateinit var binding: T
    protected lateinit var viewModel: M

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    protected lateinit var sharedPreferences: SharedPreferences

    @LayoutRes
    protected abstract fun layoutRes(): Int

    protected abstract fun viewModelClass(): Class<M>

    protected abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this, viewModelFactory)[viewModelClass()]
        dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackgroundDialog()
        setAnimationDialog()
        initView()
    }

    private fun setBackgroundDialog(){
        if (dialog != null && dialog!!.window != null){
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.setCancelable(true)
        }
    }

    fun setLayout(value: Int){
        val screenWith = Resources.getSystem().displayMetrics.widthPixels
        val params = dialog!!.window!!.attributes
        params.width =screenWith- screenWith/value
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params
    }

    private fun setAnimationDialog(){
        requireDialog().window!!.setWindowAnimations(
            R.style.dialog_animation_fade)
    }
}