package com.vladiyak.sevenwindsstudiotask.presentation.nearbycoffeeshops

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.vladiyak.sevenwindsstudiotask.R
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentNearbyCoffeeShopsBinding
import com.vladiyak.sevenwindsstudiotask.presentation.nearbycoffeeshops.adapter.CoffeeShopsAdapter
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerLocationItem
import com.vladiyak.sevenwindsstudiotask.utils.Resource
import com.yandex.mapkit.geometry.Geo
import com.yandex.mapkit.geometry.Point
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NearbyCoffeeShopsFragment : Fragment() {

    private var _binding: FragmentNearbyCoffeeShopsBinding? = null
    private val binding: FragmentNearbyCoffeeShopsBinding
        get() = _binding ?: throw RuntimeException("FragmentNearbyCoffeeShopsBinding == null")

    private val viewModel: NearbyCoffeeShopsViewModel by viewModels()
    private lateinit var adapterCoffeeShops: CoffeeShopsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNearbyCoffeeShopsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestUserPermission()
        observeData()
        setupRecyclerViews()

        binding.buttonMap.setOnClickListener {
            lifecycleScope.launch {
                viewModel.uiState.collectLatest {
                    val action =
                        NearbyCoffeeShopsFragmentDirections.actionNearbyCoffeeShopsFragmentToMapFragment(
                            it.coffeeShops[0].id
                        )
                    if (findNavController().currentDestination?.id == R.id.nearbyCoffeeShopsFragment) {
                        findNavController().navigate(action)
                    }
                }
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshAll()
            viewModel.refreshCurrentLocation()
            viewModel.getCoffeeShops()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { state ->
                    adapterCoffeeShops.submitList(state.coffeeShops)
                    state.isLoading.let {
                        when (it) {
                            true -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }

                            false -> {
                                binding.progressBar.visibility = View.INVISIBLE
                            }
                        }
                    }
                    if (state.message.isNotEmpty()) {
                        Snackbar.make(
                            requireContext(),
                            binding.layout,
                            state.message,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setupRecyclerViews() {
        adapterCoffeeShops =
            CoffeeShopsAdapter(onClickListener = object : OnClickListenerLocationItem {
                override fun onItemClick(coffeeShop: LocationItem) {
                    val action =
                        NearbyCoffeeShopsFragmentDirections.actionNearbyCoffeeShopsFragmentToMenuFragment(
                            coffeeShop.id
                        )
                    findNavController().navigate(action)
                }
            })
        binding.coffeeShopsRv.adapter = adapterCoffeeShops
        binding.coffeeShopsRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }


    private fun requestUserPermission() {
        if (requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            requireActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            requireActivity().requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 0
            )
            return
        }
    }

    private fun showLogoutAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        val view =
            LayoutInflater.from(requireContext()).inflate(R.layout.logout_alert_dialog, null, false)
        alertDialogBuilder.setView(view)

        val alertDialog = alertDialogBuilder.create()

        val btnNo = view.findViewById<Button>(R.id.btn_no)
        val btnYes = view.findViewById<Button>(R.id.btn_yes)

        btnNo.setOnClickListener {
            alertDialog.dismiss()
        }

        btnYes.setOnClickListener {
            alertDialog.dismiss()
            val sharedPrefs = context?.getSharedPreferences("main", Context.MODE_PRIVATE)
            sharedPrefs?.edit()?.remove("token")?.apply()
            val action =
                NearbyCoffeeShopsFragmentDirections.actionNearbyCoffeeShopsFragmentToSignUpFragment()
            findNavController().navigate(action)
        }

        alertDialog.show()
    }

}



