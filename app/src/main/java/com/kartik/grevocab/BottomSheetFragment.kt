package com.kartik.grevocab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet.message
import kotlinx.android.synthetic.main.bottom_sheet.positiveButton
import kotlinx.android.synthetic.main.bottom_sheet.title

class BottomSheetFragment(private val bottomSheetConfig: BottomSheetConfig) : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title.setText(bottomSheetConfig.title)
        message.text = bottomSheetConfig.message

        positiveButton.setText(bottomSheetConfig.positiveButtonText)

        positiveButton.setOnClickListener {
            bottomSheetConfig.positiveButtonAction?.invoke()
            dismiss()
        }
    }
}
