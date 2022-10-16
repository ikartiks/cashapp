package com.kartik.grevocab

import com.kartik.grevocab.base.ResProvider

class MockResProvider : ResProvider {
    override fun getString(res: Int, vararg objects: Any?): String {
        return "Hi"
    }

    override fun getColor(color: Int): Int {
        return R.color.colorAccent
    }
}
