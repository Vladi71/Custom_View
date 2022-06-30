package ru.netology.custom_view.utils

import android.content.Context
import android.util.TypedValue
import kotlin.math.ceil


object Utils {
    fun dp(context: Context, dp: Float): Int {
        return if (dp == 0F) 0 else ceil(
                context.resources.displayMetrics.density * dp
        ).toInt()
    }

    fun sp(context: Context, dp: Float): Int {
        return if (dp == 0F) 0 else {
            TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP,
                    dp,
                    context.resources.displayMetrics,
            ).toInt()
        }
    }
}