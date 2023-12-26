package com.vladiyak.sevenwindsstudiotask.presentation.menu.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem

class MenuAdapter() : ListAdapter<CoffeeItem, MenuViewHolder>(DiffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)

        holder.binding.plusButton.setOnClickListener {
            onPlusClick?.invoke(item)
        }
        holder.binding.minusButton.setOnClickListener {
            onMinusClick?.invoke(item)
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<CoffeeItem>() {
        override fun areItemsTheSame(oldItem: CoffeeItem, newItem: CoffeeItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CoffeeItem, newItem: CoffeeItem): Boolean {
            return oldItem.id == newItem.id
        }

    }

    var onPlusClick: ((CoffeeItem) -> Unit)? = null
    var onMinusClick: ((CoffeeItem) -> Unit)? = null
}