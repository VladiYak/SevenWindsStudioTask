package com.vladiyak.sevenwindsstudiotask.presentation.map

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.vladiyak.sevenwindsstudiotask.R
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.ClusterListener
import com.yandex.mapkit.map.ClusterizedPlacemarkCollection
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.TextStyle
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map) {

    private var _binding: FragmentMapBinding? = null
    private val binding: FragmentMapBinding
        get() = _binding ?: throw RuntimeException("FragmentMapBinding == null")

    private val viewModel: MapViewModel by viewModels()

    private var map: Map? = null
    private var placeMarkCollection: ClusterizedPlacemarkCollection? = null
    private var placeMarkIconImageProvider: ImageProvider? = null

    private val placeMarkTapListener = MapObjectTapListener { mapObject, _ ->
        if (mapObject is PlacemarkMapObject && mapObject.userData is Int) {
            val coffeeShopId = mapObject.userData as Int
            val action = MapFragmentDirections.actionMapFragmentToMenuFragment(coffeeShopId)
            findNavController().navigate(action)
            true
        } else {
            false
        }
    }

    private val clusterListener = ClusterListener { cluster ->
        placeMarkIconImageProvider?.let {
            cluster.appearance.apply {
                setIcon(
                    ImageProvider.fromResource(requireContext(), R.drawable.marker)
                )
            }
            cluster.addClusterTapListener { cluster ->
                return@addClusterTapListener cluster.placemarks.firstOrNull()?.let {
                    map?.move(
                        CameraPosition(
                            it.geometry,
                            DEFAULT_ZOOM,
                            DEFAULT_AZIMUTH,
                            DEFAULT_TILT
                        ),
                        DEFAULT_CAMERA_ANIMATION,
                        null
                    )
                    true
                } ?: false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        map = binding.mapView.mapWindow.map.apply {
            placeMarkCollection = mapObjects.addClusterizedPlacemarkCollection(clusterListener)
        }
        placeMarkIconImageProvider = ImageProvider.fromResource(
            requireContext(), R.drawable.marker
        )

        savedInstanceState?.run {
            map?.move(
                CameraPosition(
                    Point(
                        getDouble(KEY_CAMERA_POSITION_LATITUDE),
                        getDouble(KEY_CAMERA_POSITION_LONGITUDE)
                    ),
                    getFloat(KEY_CAMERA_POSITION_ZOOM),
                    getFloat(KEY_CAMERA_POSITION_AZIMUTH),
                    getFloat(KEY_CAMERA_POSITION_TILT)
                )
            )
        } ?: viewModel.restoreCameraPosition()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::handleUiState)
            }
        }

        binding.buttonArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        map?.cameraPosition?.let {
            viewModel.saveCameraPosition(
                CameraMovePosition(
                    it.target.latitude, it.target.longitude,
                    it.zoom, it.azimuth, it.tilt
                )
            )
        }

        map = null
        placeMarkCollection = null
        placeMarkIconImageProvider = null
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        map?.cameraPosition?.run {
            outState.putAll(
                bundleOf(
                    KEY_CAMERA_POSITION_LATITUDE to target.latitude,
                    KEY_CAMERA_POSITION_LONGITUDE to target.longitude,
                    KEY_CAMERA_POSITION_ZOOM to zoom,
                    KEY_CAMERA_POSITION_AZIMUTH to azimuth,
                    KEY_CAMERA_POSITION_TILT to tilt
                )
            )
        }
    }

    private fun handleUiState(uiState: MapUiState) {
        uiState.moveCameraTo?.let {
            map?.move(
                CameraPosition(
                    Point(it.latitude, it.longitude),
                    it.zoom ?: DEFAULT_ZOOM,
                    it.azimuth ?: DEFAULT_AZIMUTH,
                    it.tilt ?: DEFAULT_TILT
                )
            )
            viewModel.onCameraMoved()
        }

        map?.let {
            placeMarkCollection?.clear()
            uiState.coffeeShops.forEach(::addCoffeeShopOnMap)
        }
    }

    private fun addCoffeeShopOnMap(coffeeShop: LocationItem) {
        val placeMarkCollection = placeMarkCollection ?: return
        val textColor = resources.getColor(R.color.textColor, context?.theme)

        placeMarkCollection.addPlacemark().apply {
            geometry = Point(coffeeShop.point.latitude, coffeeShop.point.longitude)
            userData = coffeeShop.id
            placeMarkIconImageProvider?.let {
                setIcon(it)
            }
            setText(coffeeShop.name, TextStyle().apply {
                placement = TextStyle.Placement.BOTTOM
                color = textColor
                outlineColor = Color.TRANSPARENT
            })
            addTapListener(placeMarkTapListener)
        }
        placeMarkCollection.clusterPlacemarks(60.0, 15)
    }

    companion object {
        private const val DEFAULT_ZOOM = 8f
        private const val DEFAULT_AZIMUTH = 0f
        private const val DEFAULT_TILT = 0f

        private val DEFAULT_CAMERA_ANIMATION = Animation(Animation.Type.SMOOTH, 2f)

        private const val KEY_CAMERA_POSITION_LATITUDE = "camera_position_latitude"
        private const val KEY_CAMERA_POSITION_LONGITUDE = "camera_position_longitude"
        private const val KEY_CAMERA_POSITION_ZOOM = "camera_position_zoom"
        private const val KEY_CAMERA_POSITION_AZIMUTH = "camera_position_azimuth"
        private const val KEY_CAMERA_POSITION_TILT = "camera_position_tilt"
    }
}
