package com.vladiyak.sevenwindsstudiotask.presentation.menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem
import com.vladiyak.sevenwindsstudiotask.databinding.MenuItemRvBinding
import com.vladiyak.sevenwindsstudiotask.utils.MenuItemInteractionListener

class MenuAdapter(
    private val interactionListener: MenuItemInteractionListener? = null
) : ListAdapter<CoffeeItem, CartMenuViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartMenuViewHolder {
        val binding = MenuItemRvBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CartMenuViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: CartMenuViewHolder, position: Int) {
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
