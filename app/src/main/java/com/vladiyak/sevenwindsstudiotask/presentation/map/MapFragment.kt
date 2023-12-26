package com.vladiyak.sevenwindsstudiotask.presentation.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.vladiyak.sevenwindsstudiotask.R
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment() {

    private val startPoint: Point = Point(44.83000000000000, 44.83000000000000)


    private var _binding: FragmentMapBinding? = null
    private val binding: FragmentMapBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailBinding == null")

    private val viewModel: MapViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        viewModel.getLocations()


        binding.mapView.map
            .move(
                CameraPosition(
                    startPoint, 11.0f, 0.0f, 0.0f
                ),
                Animation(Animation.Type.SMOOTH, 0f),
                null
            )

        var mapkit: MapKit = MapKitFactory.getInstance()
        requestLocationPermission()
        var userLocation = mapkit.createUserLocationLayer(binding.mapView.mapWindow)
        userLocation.isVisible = true

        viewModel.locations.observe(viewLifecycleOwner, Observer { locationList ->
            val pointList =
                mutableListOf<Point>()
            locationList.map {
                pointList.add(Point(it.point.latitude.toDouble(), it.point.longitude.toDouble()))
            }

            Log.d("mapList", "$pointList")

            val text = binding.mapView.map.mapObjects.addPlacemarks(pointList, ImageProvider.fromResource(context, R.drawable.marker), IconStyle())
        })

//        binding.mapView.map.mapObjects.addPlacemark(
//            Point(44.83000000000000, 44.83000000000000),
//            ImageProvider.fromResource(context, R.drawable.marker)
//        )

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun requestLocationPermission() {
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

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
    }


}