package com.kartik.grevocab.base

import android.content.Context
import android.net.ConnectivityManager
import android.text.Spannable
import android.text.SpannableString
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.kartik.grevocab.ActivityMain
import com.kartik.grevocab.R
import org.koin.core.component.KoinComponent

@Suppress("JAVA_CLASS_ON_COMPANION")
abstract class FragmentBase : Fragment(), KoinComponent {

    fun showAppSheet(
        message: String,
        @StringRes title: Int = R.string.app_name,
        @StringRes positiveButtonText: Int = R.string.ok,
        positiveButtonAction: (() -> Unit)? = null,
        cancelable: Boolean = true
    ) {
        activity?.let {
            (it as ActivityBase).showAppSheet(
                message, title,
                positiveButtonText,
                positiveButtonAction,
                cancelable
            )
        }
    }

    fun toolbarTitle(title: String) {
        val s = SpannableString(title)
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_light)
        s.setSpan(typeface, 0, s.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        (activity as? ActivityMain)?.supportActionBar?.title = s
    }

    fun isConnected(): Boolean {
        val connectivityManager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnected == true
    }
}
