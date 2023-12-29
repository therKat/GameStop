package com.example.base_app.ui.widget.pattern

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes

sealed class PatternViewState {

    data class Success(
        @ColorInt val dotColor: Int,
        @ColorInt val lineColorInt: Int,
        @DrawableRes val dotBackgroundColor: Int
    ): PatternViewState()

    data class Error(
        @ColorInt val dotColor: Int,
        @ColorInt val lineColorInt: Int,
        @DrawableRes val dotBackgroundColor: Int
    ): PatternViewState()
    object  Initial: PatternViewState()
    object  Started: PatternViewState()

}