package com.vladiyak.sevenwindsstudiotask.presentation.orderdetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem
import com.vladiyak.sevenwindsstudiotask.databinding.OrderItemRvBinding
import com.vladiyak.sevenwindsstudiotask.utils.CartItemInteractionListener

class OrderDetailsAdapter(
    private val interactionListener: CartItemInteractionListener? = null
) : ListAdapter<CoffeeItem, OrderDetailsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
        val binding = OrderItemRvBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return OrderDetailsViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallback : DiffUtil.ItemCallback<CoffeeItem>() {
        override fun areItemsTheSame(oldItem: CoffeeItem, newItem: CoffeeItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CoffeeItem, newItem: CoffeeItem): Boolean =
            oldItem == newItem
    }
}

