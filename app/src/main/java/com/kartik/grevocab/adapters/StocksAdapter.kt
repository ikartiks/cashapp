package com.kartik.grevocab.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kartik.grevocab.adapters.display.StockDisplay
import com.kartik.grevocab.adapters.display.getEmptyStocks
import com.kartik.grevocab.adapters.vh.StocksViewHolder
import com.kartik.grevocab.databinding.InflateStocksBinding

class StocksAdapter(val clickListener: OnBindClickListener? = null) : RecyclerView.Adapter<BaseBindingViewHolder>() {
    private var items: ArrayList<StockDisplay> = ArrayList()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder, position: Int) {
        holder as StocksViewHolder
        holder.binding.stockDisplay = items[position]
        holder.binding.executePendingBindings()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder {
        return StocksViewHolder(
            InflateStocksBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            clickListener
        )
    }

    fun replaceData(items: ArrayList<StockDisplay>) {
        if (items.isEmpty()) {
            items.add(getEmptyStocks())
        }
        this.items = items
        notifyDataSetChanged()
    }
}
