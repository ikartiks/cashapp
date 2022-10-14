package com.kartik.grevocab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kartik.grevocab.api.utility.APIResponse
import com.kartik.grevocab.api.utility.Preferences
import com.kartik.grevocab.base.FragmentBase
import kotlinx.android.synthetic.main.fragment_settings.mode
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FragmentSettings : FragmentBase(), KoinComponent {

    private val preferences: Preferences by inject()

    // todo use data binding,
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mode.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.success -> {
                    preferences.updateApiResponse(APIResponse.SUCCESS)
                }
                R.id.failure -> {
                    preferences.updateApiResponse(APIResponse.FAILURE)
                }
                R.id.empty -> {
                    preferences.updateApiResponse(APIResponse.EMPTY)
                }
            }
        }
    }
}
