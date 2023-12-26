package com.vladiyak.sevenwindsstudiotask.presentation.orderdetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.vladiyak.sevenwindsstudiotask.R
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentMenuBinding
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentOrderDetailsBinding
import com.vladiyak.sevenwindsstudiotask.presentation.menu.MenuFragmentArgs

class OrderDetailsFragment : Fragment() {


    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding: FragmentOrderDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentOrderDetailsBinding == null")

    private val args: OrderDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.cartitems.forEach {
            Log.d("CartProducts", "$it")
        }

        Log.d("CartProducts", "${args.cartitems}")
    }

}