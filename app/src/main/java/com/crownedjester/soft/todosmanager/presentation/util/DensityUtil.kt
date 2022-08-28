package com.crownedjester.soft.todosmanager.presentation.util

import android.content.Context
import java.lang.ref.WeakReference

object DensityUtil {

    fun convertDpToPx(context: WeakReference<Context>, dpValue: Float) =
        with(context.get()!!.resources.displayMetrics.density) {
            val scale = this
            context.clear()
            dpValue * scale + 0.5f
        }

    fun convertPxToDp(context: WeakReference<Context>, pxValue: Float) =
        with(context.get()!!.resources.displayMetrics.density) {
            val scale = this
            context.clear()
            pxValue / scale + 0.5f
        }

}