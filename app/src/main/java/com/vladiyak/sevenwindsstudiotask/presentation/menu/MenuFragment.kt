package com.vladiyak.sevenwindsstudiotask.presentation.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vladiyak.sevenwindsstudiotask.R
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentMenuBinding
import com.vladiyak.sevenwindsstudiotask.presentation.menu.adapter.MenuAdapter
import com.vladiyak.sevenwindsstudiotask.utils.MenuItemInteractionListener
import com.vladiyak.sevenwindsstudiotask.utils.SnackBarAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding: FragmentMenuBinding
        get() = _binding ?: throw RuntimeException("FragmentMenuBinding == null")

    private val viewModel: MenuViewModel by viewModels()
    private lateinit var adapterMenu: MenuAdapter
    private val interactionListener = object : MenuItemInteractionListener {
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
        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()

        with(binding) {
            swipeRefresh.setOnRefreshListener {
                viewModel.refresh()
            }
            buttonPay.setOnClickListener {
                val action = MenuFragmentDirections.actionMenuFragmentToOrderDetailsFragment()
                findNavController().navigate(action)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect(::handleUiState)
                }
                launch {
                    viewModel.uiEvent.collect(::handleUiEvent)
                }
            }
        }

        binding.buttonArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun handleUiState(uiState: MenuUiState) {
        with(binding) {
            swipeRefresh.isRefreshing = uiState.isLoading
            buttonPay.isEnabled = uiState.canProceed
        }
        adapterMenu.submitList(uiState.menuItems)
    }

    private fun handleUiEvent(uiEvent: MenuUiEvent) {
        val action = SnackBarAction(getString(R.string.snackbar_retry)) {
            viewModel.refresh()
        }

        when (uiEvent) {
            MenuUiEvent.ErrorConnection -> showSnackbar(
                R.string.error_connection, action
            )

            MenuUiEvent.ErrorLoading -> showSnackbar(
                R.string.error_loading_menu, action
            )
        }
    }


    private fun setupRecyclerViews() {
        adapterMenu = MenuAdapter(interactionListener)
        binding.menuRv.adapter = adapterMenu
        binding.menuRv.layoutManager =
            GridLayoutManager(requireContext(), 2)
    }

    private fun showSnackbar(
        @StringRes messageResId: Int,
        snackbarAction: SnackBarAction? = null
    ) {
        Snackbar.make(
            binding.root, messageResId, Snackbar.LENGTH_SHORT
        ).apply {
            anchorView = binding.buttonPay
            snackbarAction?.let {
                setAction(it.label, it.listener)
            }
        }.show()
    }

}