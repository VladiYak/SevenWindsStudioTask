package com.vladiyak.sevenwindsstudiotask.presentation.menu.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem
import com.vladiyak.sevenwindsstudiotask.databinding.MenuItemRvBinding
import com.vladiyak.sevenwindsstudiotask.utils.MenuItemInteractionListener
import com.vladiyak.sevenwindsstudiotask.utils.addSuffix

class CartMenuItemViewHolder(
    private val binding: MenuItemRvBinding,
    private val interactionListener: MenuItemInteractionListener? = null
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CoffeeItem) {
        itemView.setOnClickListener {
            interactionListener?.onClick(item, bindingAdapterPosition)
        }
        binding.apply {
            Glide.with(itemView).load(item.imageURL)
                .into(imageCoffee)
            count.text = item.quantity.toString()
            coffeeText.text = item.name
            price.text = item.price.toString().addSuffix(" руб")

            minusButton.setOnClickListener {
                interactionListener?.onRemove(item, bindingAdapterPosition)
            }
            plusButton.setOnClickListener {
                interactionListener?.onAdd(item, bindingAdapterPosition)
            }
        }
    }
}