package com.example.base_app.extension

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.base_app.R

fun View.beGone(){
    this.visibility = View.GONE
}

fun View.beInvisible(){
    this.visibility = View.INVISIBLE
}

fun View.beVisible(){
    this.visibility = View.VISIBLE
}

fun TextView.setResourceColor(resId: Int){
    this.setTextColor(ContextCompat.getColor(this.context, resId))
}

fun ImageView.setResourceColor(resId: Int){
    this.setColorFilter(ContextCompat.getColor(this.context, resId))
}

fun ImageView.loadPath(path: String){
    Glide.with(context).load(path).into(this)
}

fun View.onClickListener(onClick:(View) -> Unit){
    setOnClickListener {
        if (it.isEnabled){
            it.isEnabled = false
            onClick(this)
            it.postDelayed({it.isEnabled = true}, 500)
        }
    }
}

fun EditText.onTextChangedListener(onTextChanged:(String)->Unit){
    this.addTextChangedListener(object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged(s.toString())
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}

fun ViewGroup.slideUpAnimation(){
    this.beVisible()
    val animationTopic = AnimationUtils.loadAnimation(context, R.anim.bottom_sheet_slide)
    startAnimation(animationTopic)
}
fun ViewGroup.slideDownAnimation(){
    val animationTopic = AnimationUtils.loadAnimation(context, R.anim.bottom_sheet_slide_down)
    animationTopic.setAnimationListener(object : Animation.AnimationListener{
        override fun onAnimationStart(p0: Animation?) { }
        override fun onAnimationEnd(p0: Animation?) { beGone() }
        override fun onAnimationRepeat(p0: Animation?) { }
    })
    startAnimation(animationTopic)
}

fun View.hideKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.hideSoftInputFromWindow(
        this.windowToken,
        0
    )
}

fun View.showKeyboard(){
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.showSoftInput(
        this,
        InputMethodManager.SHOW_IMPLICIT
    )
}

