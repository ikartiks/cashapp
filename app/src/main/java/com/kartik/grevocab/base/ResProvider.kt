package com.kartik.grevocab.base

import android.app.Application
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

interface ResProvider {

    fun getString(@StringRes res: Int, vararg objects: Any?): String
    fun getColor(@ColorRes color: Int): Int
}

class AndroidResProvider(private val context: Application) : ResProvider {

    override fun getString(@StringRes res: Int, vararg objects: Any?): String {
        return context.getString(res, *objects)
    }

    override fun getColor(color: Int): Int {
        return ContextCompat.getColor(context, color)
    }
}
