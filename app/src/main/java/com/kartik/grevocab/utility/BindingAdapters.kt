package com.kartik.grevocab.utility

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("is_selected")
fun setSelected(view: View, selected: Boolean) {
    view.isSelected = selected
}

@BindingAdapter("show")
fun show(view: View, show: Boolean) {
    if (show)
        view.visibility = View.VISIBLE
    else
        view.visibility = View.GONE
}

@BindingAdapter("bgColor")
fun bgColor(view: View, color: Int) {
    view.setBackgroundColor(color)
}

@BindingAdapter("textColor")
fun textColor(view: TextView, color: Int) {
    view.setTextColor(ContextCompat.getColor(view.context, color))
}

@BindingAdapter("errorMessage")
fun errorMessage(textInputLayout: TextInputLayout, errorText: String?) {
    if (errorText != null && errorText.isNotBlank()) {
        textInputLayout.isErrorEnabled = true
        textInputLayout.error = errorText
    } else {
        textInputLayout.error = null
        textInputLayout.isErrorEnabled = false
    }
}
