package com.kartik.grevocab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kartik.grevocab.base.FragmentBase
import com.kartik.grevocab.databinding.FragmentSplashBinding
import kotlinx.android.synthetic.main.fragment_splash.logo
import org.koin.core.component.KoinComponent

class FragmentSplash : FragmentBase(), KoinComponent {

    lateinit var binding: FragmentSplashBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        (activity as ActivityMain).setToolbarVisibility(View.GONE)
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logo.postDelayed(
            {
                findNavController().navigate(FragmentSplashDirections.actionFragmentSplashToFragmentWordList())
            },
            3000
        )
    }
}
