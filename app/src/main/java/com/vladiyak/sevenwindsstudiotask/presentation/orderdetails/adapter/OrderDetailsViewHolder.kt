package com.vladiyak.sevenwindsstudiotask.presentation.orderdetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem
import com.vladiyak.sevenwindsstudiotask.databinding.OrderItemRvBinding
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerMinusButton
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerPlusButton

class OrderDetailsViewHolder(
    val binding: OrderItemRvBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CoffeeItem, onClickListenerPlusButton: OnClickListenerPlusButton, onClickListenerMinusButton: OnClickListenerMinusButton) {
        binding.apply {
            buttonPlus.setOnClickListener {
                onClickListenerPlusButton.onItemClick(item)
            }
            buttonMinus.setOnClickListener {
                onClickListenerMinusButton.onItemClick(item)
            }
            coffeeName.text = item.name
            price.text = item.price.toString()
            count.text = item.quantity.toString()
        }
    }


    companion object {
        fun from(parent: ViewGroup): OrderDetailsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = OrderItemRvBinding.inflate(layoutInflater, parent, false)
            return OrderDetailsViewHolder(binding)
        }
    }
}