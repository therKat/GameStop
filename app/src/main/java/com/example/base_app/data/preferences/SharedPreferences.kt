package com.example.base_app.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.base_app.utils.constants.Constants
import javax.inject.Inject


class SharedPreferences @Inject constructor(private val context: Context) {

    private fun getPref(context : Context): SharedPreferences {
        return context.getSharedPreferences(
            context.packageName,
            Context.MODE_PRIVATE
        )
    }

    var isFirstOpen: Boolean
        get() = getPref(context).getBoolean(Constants.FIRST_OPEN_APP, true)
        set(isFirstOpen) = getPref(context).edit().putBoolean(Constants.FIRST_OPEN_APP, isFirstOpen).apply()

    var screenCurrent: String
        get() = getPref(context).getString(Constants.SCREEN_CURRENT, "lang") ?: ""
        set(screenCurrent) = getPref(context).edit().putString(Constants.SCREEN_CURRENT, screenCurrent).apply()
    var langCurrent: String
        get() = getPref(context).getString(Constants.LANGUAGE_CURRENT, "en") ?: ""
        set(langCurrent) = getPref(context).edit().putString(Constants.LANGUAGE_CURRENT, langCurrent).apply()

    var girdStyle: Int
        get() = getPref(context).getInt(Constants.GRID_STYLE, Constants.GRID_STYLE_THREE_COLUMN)
        set(girdStyle) = getPref(context).edit().putInt(Constants.GRID_STYLE, girdStyle).apply()

    var isSort: Boolean
        get() = getPref(context).getBoolean(Constants.KEY_SORT, false)
        set(isSort) = getPref(context).edit().putBoolean(Constants.KEY_SORT, isSort).apply()

    var typeOfLock: Int
        get() = getPref(context).getInt(Constants.TYPE_OF_LOCK, -1)
        set(typeOfLock) = getPref(context).edit().putInt(Constants.TYPE_OF_LOCK, typeOfLock).apply()

    var numRate: Int
        get() = getPref(context).getInt(Constants.NUM_RATE, -1)
        set(numRate) = getPref(context).edit().putInt(Constants.NUM_RATE, numRate).apply()

    var passwordLock: String
        get() = getPref(context).getString(Constants.PASSWORD_LOCK, "") ?: ""
        set(passwordLock) = getPref(context).edit().putString(Constants.PASSWORD_LOCK, passwordLock).apply()

    var securityAnswer: String
        get() = getPref(context).getString(Constants.SECURITY_QUESTION, "") ?: ""
        set(securityAnswer) = getPref(context).edit().putString(Constants.SECURITY_QUESTION, securityAnswer).apply()

}