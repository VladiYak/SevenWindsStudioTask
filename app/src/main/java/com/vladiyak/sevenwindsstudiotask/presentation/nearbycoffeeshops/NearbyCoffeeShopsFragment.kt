package com.vladiyak.sevenwindsstudiotask.presentation.nearbycoffeeshops

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentNearbyCoffeeShopsBinding
import com.vladiyak.sevenwindsstudiotask.presentation.nearbycoffeeshops.adapter.CoffeeShopsAdapter
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerLocationItem
import dagger.hilt.android.AndroidEntryPoint


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

        viewModel.getCoffeeShops()

        setupRecyclerViews()

        viewModel.coffeeShop.observe(viewLifecycleOwner, Observer {
            adapterCoffeeShops.submitList(it.toList())
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
}



