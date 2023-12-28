package com.vladiyak.sevenwindsstudiotask.presentation.orderdetails.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerMinusButton
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerPlusButton

class OrderDetailsAdapter(
    private val onClickListenerPlusButton: OnClickListenerPlusButton,
    private val onClickListenerMinusButton: OnClickListenerMinusButton
) : ListAdapter<CoffeeItem, OrderDetailsViewHolder>(DiffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
        return OrderDetailsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item, onClickListenerPlusButton, onClickListenerMinusButton)
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<CoffeeItem>() {
        override fun areItemsTheSame(oldItem: CoffeeItem, newItem: CoffeeItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CoffeeItem, newItem: CoffeeItem): Boolean {
            return oldItem.id == newItem.id
        }

    }
}