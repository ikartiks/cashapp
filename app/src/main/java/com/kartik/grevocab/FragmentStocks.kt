package com.kartik.grevocab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kartik.grevocab.adapters.StocksAdapter
import com.kartik.grevocab.base.FragmentBase
import com.kartik.grevocab.databinding.FragmentStocksBinding
import com.kartik.grevocab.utility.LoaderState
import com.kartik.grevocab.vm.FragmentStocksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentStocks : FragmentBase() {

    lateinit var binding: FragmentStocksBinding
    private val vm: FragmentStocksViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentStocksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarTitle(resources.getString(R.string.app_name))
        (activity as ActivityMain).setToolbarVisibility(View.VISIBLE)

        val adapter = StocksAdapter()
        binding.recyclerView.adapter = adapter

        CoroutineScope(Dispatchers.Main).launch {
            val list = vm.getStocks()
            list?.let { adapter.replaceData(it) }
        }
    }

    override fun onResume() {
        super.onResume()
        vm.loaderLiveData.observe(viewLifecycleOwner) {
            when (it) {
                LoaderState.ERROR -> {
                    binding.loader.visibility = View.GONE
                    showAppSheet(getString(R.string.api_error))
                }
                LoaderState.LOADING -> {
                    binding.loader.visibility = View.VISIBLE
                    binding.loader.playAnimation()
                }
                LoaderState.DONE -> binding.loader.visibility = View.GONE
            }
        }
    }

    override fun onStop() {
        super.onStop()
        vm.loaderLiveData.removeObservers(viewLifecycleOwner)
    }
}
