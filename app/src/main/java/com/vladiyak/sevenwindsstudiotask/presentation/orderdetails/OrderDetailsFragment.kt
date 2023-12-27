package com.vladiyak.sevenwindsstudiotask.presentation.orderdetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentOrderDetailsBinding
import com.vladiyak.sevenwindsstudiotask.presentation.orderdetails.adapter.OrderDetailsAdapter
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerMinusButton
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerPlusButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailsFragment : Fragment() {


    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding: FragmentOrderDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentOrderDetailsBinding == null")

    private lateinit var adapterOrderDetails: OrderDetailsAdapter
    private val args: OrderDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        val cartItems = args.cartitems.toList()
        adapterOrderDetails.submitList(cartItems)
        binding.buttonPay.setOnClickListener {
            val action = OrderDetailsFragmentDirections.actionOrderDetailsFragmentToOrderAcceptedFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerViews() {
        adapterOrderDetails =
            OrderDetailsAdapter(onClickListenerPlusButton = object : OnClickListenerPlusButton {
                override fun onItemClick(coffeeItem: CoffeeItem) {
                    coffeeItem.quantity += 1
                    adapterOrderDetails.notifyDataSetChanged()
                }

            }, onClickListenerMinusButton = object : OnClickListenerMinusButton {
                override fun onItemClick(coffeeItem: CoffeeItem) {
                    if (coffeeItem.quantity > 0) {
                        coffeeItem.quantity -= 1
                    }
                    adapterOrderDetails.notifyDataSetChanged()
                }

            })
        binding.orderDetailsRv.adapter = adapterOrderDetails
        binding.orderDetailsRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }
}
