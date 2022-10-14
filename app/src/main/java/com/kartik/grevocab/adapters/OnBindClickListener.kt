package com.kartik.grevocab.adapters

import android.view.View

interface OnBindClickListener {
    fun onItemClick(view: View, position: Int, item: Any)
}
