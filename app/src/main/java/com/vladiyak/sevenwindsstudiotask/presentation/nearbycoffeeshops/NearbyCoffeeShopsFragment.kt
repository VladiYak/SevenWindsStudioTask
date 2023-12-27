package com.vladiyak.sevenwindsstudiotask.presentation.nearbycoffeeshops

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentNearbyCoffeeShopsBinding
import com.vladiyak.sevenwindsstudiotask.presentation.nearbycoffeeshops.adapter.CoffeeShopsAdapter
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerLocationItem
import com.vladiyak.sevenwindsstudiotask.utils.Resource
import com.yandex.mapkit.geometry.Geo
import com.yandex.mapkit.geometry.Point
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NearbyCoffeeShopsFragment : Fragment() {

    private var _binding: FragmentNearbyCoffeeShopsBinding? = null
    private val binding: FragmentNearbyCoffeeShopsBinding
        get() = _binding ?: throw RuntimeException("FragmentNearbyCoffeeShopsBinding == null")

    private val viewModel: NearbyCoffeeShopsViewModel by viewModels()
    private lateinit var adapterCoffeeShops: CoffeeShopsAdapter
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var myLocation: Point = Point(0.0, 0.0)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNearbyCoffeeShopsBinding.inflate(inflater, container, false)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestUserPermission()
        viewModel.getCoffeeShops()
        setupRecyclerViews()

        viewModel.coffeeShop.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    adapterCoffeeShops.submitList(response.data)
                    val pointList =
                        mutableListOf<Point>()
                    response.data?.map {
                        pointList.add(
                            Point(
                                it.point.latitude.toDouble(),
                                it.point.longitude.toDouble()
                            )
                        )
                    }

                    val distance = Geo.distance(
                        myLocation, pointList[0]
                    )

                    val distance2 = Geo.distance(
                        myLocation, pointList[1]
                    )

                    val distance3 = Geo.distance(
                        myLocation, pointList[2]
                    )

                    response.data?.let { viewModel.setDistance(it, response.data[0].id, distance) }
                    adapterCoffeeShops.notifyItemChanged(0)

                    response.data?.let { viewModel.setDistance(it, response.data[1].id, distance2) }
                    adapterCoffeeShops.notifyItemChanged(1)

                    response.data?.let { viewModel.setDistance(it, response.data[2].id, distance3) }
                    adapterCoffeeShops.notifyItemChanged(2)
                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {
                    Snackbar.make(view, "Error", Snackbar.LENGTH_SHORT).show()
                }

            }


        })

        binding.buttonMap.setOnClickListener {
            val action =
                NearbyCoffeeShopsFragmentDirections.actionNearbyCoffeeShopsFragmentToMapFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerViews() {
        adapterCoffeeShops =
            CoffeeShopsAdapter(onClickListener = object : OnClickListenerLocationItem {
                override fun onItemClick(coffeeShop: LocationItem) {
                    val action =
                        NearbyCoffeeShopsFragmentDirections.actionNearbyCoffeeShopsFragmentToMenuFragment(
                            coffeeShop.id.toString()
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

        val location = fusedLocationProviderClient.lastLocation
        location.addOnSuccessListener {
            if (it != null) {
                val latitude = it.latitude
                val longitude = it.longitude

                myLocation = Point(latitude, longitude)
            }
        }

    }

}



