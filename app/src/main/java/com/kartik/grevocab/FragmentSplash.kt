package com.kartik.grevocab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kartik.grevocab.base.FragmentBase
import com.kartik.grevocab.vm.FragmentSplashViewModel
import kotlinx.android.synthetic.main.fragment_splash.logo
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class FragmentSplash : FragmentBase(), KoinComponent {

    private val viewModel: FragmentSplashViewModel by viewModel()

    // todo use data binding,
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        (activity as ActivityMain).setToolbarVisibility(View.GONE)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigatePostDelay()
    }

    private fun navigatePostDelay() {
        logo?.postDelayed(
            {
                findNavController().navigate(FragmentSplashDirections.actionFragmentSplashToFragmentWordList())
            },
            3000
        )
    }
}
