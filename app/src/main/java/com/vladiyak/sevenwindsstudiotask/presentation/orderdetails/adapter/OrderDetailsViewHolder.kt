package com.vladiyak.sevenwindsstudiotask.presentation.orderdetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vladiyak.sevenwindsstudiotask.R
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem
import com.vladiyak.sevenwindsstudiotask.databinding.OrderItemRvBinding
import com.vladiyak.sevenwindsstudiotask.utils.CartItemInteractionListener
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerMinusButton
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerPlusButton
import com.vladiyak.sevenwindsstudiotask.utils.addSuffix

class CartItemViewHolder(
    private val binding: OrderItemRvBinding,
    private val interactionListener: CartItemInteractionListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cartMenuItem: CoffeeItem) {
        with(binding) {
            coffeeName.text = cartMenuItem.name
            price.text = itemView.context.getString(R.string.price, cartMenuItem.price)
            count.text = cartMenuItem.quantity.toString()

            buttonMinus.isEnabled = cartMenuItem.quantity > 0

            buttonMinus.setOnClickListener {
                interactionListener?.onRemove(cartMenuItem, bindingAdapterPosition)
            }
            buttonPlus.setOnClickListener {
                interactionListener?.onAdd(cartMenuItem, bindingAdapterPosition)
            }
        }
    }
}