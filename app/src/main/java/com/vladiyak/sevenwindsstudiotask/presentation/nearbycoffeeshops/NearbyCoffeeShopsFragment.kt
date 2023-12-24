package com.vladiyak.sevenwindsstudiotask.presentation.nearbycoffeeshops

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vladiyak.sevenwindsstudiotask.R
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentMapBinding
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentNearbyCoffeeShopsBinding


class NearbyCoffeeShopsFragment : Fragment() {

    private var _binding: FragmentNearbyCoffeeShopsBinding? = null
    private val binding: FragmentNearbyCoffeeShopsBinding
        get() = _binding ?: throw RuntimeException("FragmentNearbyCoffeeShopsBinding == null")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNearbyCoffeeShopsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonMap.setOnClickListener {
            val action = NearbyCoffeeShopsFragmentDirections.actionNearbyCoffeeShopsFragmentToMapFragment()
            findNavController().navigate(action)
        }
    }

}