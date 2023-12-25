package com.vladiyak.sevenwindsstudiotask.presentation.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentMenuBinding
import com.vladiyak.sevenwindsstudiotask.presentation.menu.adapter.MenuAdapter
import com.vladiyak.sevenwindsstudiotask.presentation.nearbycoffeeshops.NearbyCoffeeShopsFragmentArgs
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerCoffeeItem
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding: FragmentMenuBinding
        get() = _binding ?: throw RuntimeException("FragmentMenuBinding == null")

    private val viewModel: MenuViewModel by viewModels()
    private lateinit var adapterMenu: MenuAdapter
    private val args: MenuFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCoffeeItem(args.id, "Bearer ${args.token.token}")

        setupRecyclerViews()

        viewModel.coffeeItem.observe(viewLifecycleOwner, Observer {
            adapterMenu.submitList(it)
        })
    }

    private fun setupRecyclerViews() {
        adapterMenu = MenuAdapter(onClickListener = object : OnClickListenerCoffeeItem {
            override fun onItemClick(coffeeItem: CoffeeItem) {
                val action =
                    MenuFragmentDirections.actionMenuFragmentToOrderDetailsFragment()
                findNavController().navigate(action)
            }
        })
        binding.menuRv.adapter = adapterMenu
        binding.menuRv.layoutManager =
            GridLayoutManager(requireContext(), 2)
    }

}