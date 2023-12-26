package com.vladiyak.sevenwindsstudiotask.presentation.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentMenuBinding
import com.vladiyak.sevenwindsstudiotask.presentation.menu.adapter.MenuAdapter
import com.vladiyak.sevenwindsstudiotask.utils.correctId
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

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCoffeeItem(args.id, "Bearer ${args.token.token}")

        setupRecyclerViews()

        viewModel.coffeeItem.observe(viewLifecycleOwner, Observer { coffeeList ->
            adapterMenu.submitList(coffeeList.toMutableList())
            adapterMenu.onPlusClick = {
                viewModel.increaseQuantity(coffeeList, it)
                adapterMenu.notifyItemChanged(correctId(it) - 1, Unit)

            }
            adapterMenu.onMinusClick = {
                if (it.quantity > 0)
                viewModel.decreaseQuantity(coffeeList, it)
                adapterMenu.notifyItemChanged(correctId(it) - 1, Unit)
            }

            Log.d("AdapterTest", "${adapterMenu.currentList}")

            val list = adapterMenu.currentList.toMutableList().filter {
                it.quantity > 0
            }

            Log.d("ListTest", "$list")


            binding.buttonPay.setOnClickListener {
                val action = MenuFragmentDirections.actionMenuFragmentToOrderDetailsFragment(
                    list.toTypedArray()
                )
                findNavController().navigate(action)
            }
        })


        binding.buttonArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerViews() {
        adapterMenu = MenuAdapter()
        binding.menuRv.adapter = adapterMenu
        binding.menuRv.layoutManager =
            GridLayoutManager(requireContext(), 2)
    }

}