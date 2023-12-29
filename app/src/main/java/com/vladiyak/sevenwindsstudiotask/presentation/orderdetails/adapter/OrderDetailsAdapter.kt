package com.vladiyak.sevenwindsstudiotask.presentation.orderdetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vladiyak.sevenwindsstudiotask.R
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem
import com.vladiyak.sevenwindsstudiotask.databinding.OrderItemRvBinding
import com.vladiyak.sevenwindsstudiotask.utils.CartItemInteractionListener
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerMinusButton
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerPlusButton

class OrderDetailsAdapter(
    private val interactionListener: CartItemInteractionListener? = null
) : ListAdapter<CoffeeItem, CartItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val binding = OrderItemRvBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CartItemViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
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

