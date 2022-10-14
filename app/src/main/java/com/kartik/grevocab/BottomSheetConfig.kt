package com.kartik.grevocab

data class BottomSheetConfig(
    val message: String,
    var title: Int = R.string.app_name,
    var positiveButtonText: Int = R.string.ok,
    var positiveButtonAction: (() -> Unit)? = null,
    var cancelable: Boolean = true
)
