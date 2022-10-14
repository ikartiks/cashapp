package com.kartik.grevocab.adapters

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseBindingViewHolder(open val viewBinding: ViewBinding) : RecyclerView.ViewHolder(viewBinding.root)
