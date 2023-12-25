package com.vladiyak.sevenwindsstudiotask.presentation.menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem
import com.vladiyak.sevenwindsstudiotask.databinding.MenuItemRvBinding
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerCoffeeItem

class MenuViewHolder(
    val binding: MenuItemRvBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CoffeeItem, onClickListener: OnClickListenerCoffeeItem) {
        binding.apply {
            materialCardCoffeeItem.setOnClickListener {
                onClickListener.onItemClick(item)
            }

            Glide.with(itemView).load(item.imageURL)
                .into(imageCoffee)
            coffeeText.text = item.name
            price.text = item.price.toString()
        }
    }


    companion object {
        fun from(parent: ViewGroup): MenuViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = MenuItemRvBinding.inflate(layoutInflater, parent, false)
            return MenuViewHolder(binding)
        }
    }
}