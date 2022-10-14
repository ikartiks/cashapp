package com.kartik.grevocab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kartik.grevocab.adapters.OnBindClickListener
import com.kartik.grevocab.adapters.StocksAdapter
import com.kartik.grevocab.base.FragmentBase
import com.kartik.grevocab.databinding.FragmentStocksBinding
import com.kartik.grevocab.vm.FragmentStocksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentStocks : FragmentBase() {

    lateinit var adapter: StocksAdapter
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

        adapter = StocksAdapter(object : OnBindClickListener {
            override fun onItemClick(view: View, position: Int, item: Any) {}
        })
        binding.recyclerView.adapter = adapter

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val list = vm.getStocks()
                adapter.replaceData(list)
            } catch (e: RuntimeException) {
                showAppSheet(getString(R.string.api_error))
            }
        }
    }
}
