package com.kartik.grevocab.adapters.vh

import android.view.View
import com.kartik.grevocab.adapters.BaseBindingViewHolder
import com.kartik.grevocab.adapters.OnBindClickListener
import com.kartik.grevocab.databinding.InflateStocksBinding

class StocksViewHolder(val binding: InflateStocksBinding, private val clickListener: OnBindClickListener? = null) : BaseBindingViewHolder(binding), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        clickListener?.onItemClick(v, this.adapterPosition, binding.stockDisplay as Any)
    }
}
