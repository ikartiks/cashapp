package com.kartik.grevocab.base

import android.content.Context
import android.net.ConnectivityManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.kartik.grevocab.BottomSheetConfig
import com.kartik.grevocab.BottomSheetFragment
import com.kartik.grevocab.R

abstract class ActivityBase : AppCompatActivity() {

    private lateinit var bottomSheetFragment: BottomSheetFragment

    @Suppress("DEPRECATION")
    fun isConnected(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnected == true
    }

    fun showAppSheet(
        message: String,
        @StringRes title: Int = R.string.app_name,
        @StringRes positiveButtonText: Int = R.string.ok,
        positiveButtonAction: (() -> Unit)? = null,
        cancelable: Boolean = true
    ): BottomSheetFragment {
        val bottomSheetFragment = BottomSheetFragment(
            BottomSheetConfig(
                message,
                title,
                positiveButtonText,
                positiveButtonAction
            )
        )
        bottomSheetFragment.isCancelable = cancelable
        bottomSheetFragment.show(supportFragmentManager, null)
        this.bottomSheetFragment = bottomSheetFragment
        return bottomSheetFragment
    }

    fun hideBottomSheet() {
        if (this::bottomSheetFragment.isInitialized && bottomSheetFragment.isAdded) {
            bottomSheetFragment.dismiss()
        }
    }
}
