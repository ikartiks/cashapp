package com.kartik.grevocab.vm

import androidx.lifecycle.ViewModel
import com.kartik.grevocab.api.ApiService
import com.kartik.grevocab.api.utility.Preferences
import org.koin.core.component.KoinComponent

class FragmentSplashViewModel(private val preferences: Preferences, private val apiService: ApiService) : ViewModel(), KoinComponent
