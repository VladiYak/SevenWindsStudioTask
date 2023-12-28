package com.vladiyak.sevenwindsstudiotask.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.vladiyak.sevenwindsstudiotask.R
import com.vladiyak.sevenwindsstudiotask.data.models.signup.User
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentLoginBinding
import com.vladiyak.sevenwindsstudiotask.utils.Resource
import com.vladiyak.sevenwindsstudiotask.utils.TokenInstance
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding ?: throw RuntimeException("FragmentLoginBinding == null")

    private val viewModel by viewModels<LoginViewModel>()


    private val token = TokenInstance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val login = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val user = User(login, password)
            viewModel.login(user)

            viewModel.token.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        token.addToken(response.data)
                        val action =
                            LoginFragmentDirections.actionLoginFragmentToNearbyCoffeeShopsFragment()
                        if (findNavController().currentDestination?.id == R.id.loginFragment) {
                            findNavController().navigate(action)
                        }
                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Error -> {
                        Snackbar.make(view, getString(R.string.login_error), Snackbar.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }
}
