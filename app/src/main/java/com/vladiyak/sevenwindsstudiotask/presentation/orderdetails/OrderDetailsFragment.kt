package com.vladiyak.sevenwindsstudiotask.presentation.orderdetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vladiyak.sevenwindsstudiotask.R
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentOrderDetailsBinding
import com.vladiyak.sevenwindsstudiotask.presentation.nearbycoffeeshops.NearbyCoffeeShopsFragmentDirections
import com.vladiyak.sevenwindsstudiotask.presentation.nearbycoffeeshops.adapter.CoffeeShopsAdapter
import com.vladiyak.sevenwindsstudiotask.presentation.orderdetails.adapter.OrderDetailsAdapter
import com.vladiyak.sevenwindsstudiotask.utils.CartItemInteractionListener
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerLocationItem
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerMinusButton
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerPlusButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderDetailsFragment : Fragment() {


    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding: FragmentOrderDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentOrderDetailsBinding == null")

    private lateinit var adapterOrderDetails: OrderDetailsAdapter
    private val viewModel: CartViewModel by viewModels()


    private val interactionListener = object : CartItemInteractionListener {

        override fun onAdd(cartMenuItem: CoffeeItem, position: Int) {
            viewModel.addToCart(cartMenuItem.id)
        }

        override fun onRemove(cartMenuItem: CoffeeItem, position: Int) {
            viewModel.removeFromCart(cartMenuItem.id)
        }
    }


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

        with(binding) {
            buttonPay.setOnClickListener {
                viewModel.clearCart()
                val action =
                    OrderDetailsFragmentDirections.actionOrderDetailsFragmentToOrderAcceptedFragment()
                findNavController().navigate(action)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
               viewModel.uiState.collect(::handleUiState)
            }
        }
    }

    private fun setupRecyclerViews() {
        adapterOrderDetails =
            OrderDetailsAdapter(interactionListener)
        binding.orderDetailsRv.adapter = adapterOrderDetails
        binding.orderDetailsRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun handleUiState(uiState: UiState) {
        with(binding) {
            if (uiState.isLoading) {
                progressBar.show()
                orderDetailsRv.isVisible = false
            } else {
                progressBar.hide()
                orderDetailsRv.isVisible = true
            }

            emptyText.isVisible = !uiState.isLoading && uiState.isEmpty

            buttonPay.isEnabled = uiState.canProceed
            buttonPay.text = getString(R.string.pay, uiState.totalPrice)

            adapterOrderDetails.submitList(uiState.menuItems)
            Log.d("AdapterTest", "${uiState.menuItems}")
        }
    }
}




