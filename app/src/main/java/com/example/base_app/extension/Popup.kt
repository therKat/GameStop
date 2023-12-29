package com.example.base_app.extension

import android.view.View
import android.widget.PopupWindow
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun DialogFragment.turnOff(){
    if (this.isAdded){
        dismiss()
    }
}

fun DialogFragment.turnOn(fragmentManager: FragmentManager){
    if (!this.isAdded){
        show(fragmentManager, "")
    }
}

fun PopupWindow.turnOn(v: View){
    if (!this.isShowing){
        showAsDropDown(v, 0, 45)
    }
}

fun PopupWindow.turnOff(){
    if (this.isShowing){
        dismiss()
    }
}