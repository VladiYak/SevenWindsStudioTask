package com.vladiyak.sevenwindsstudiotask.presentation.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.vladiyak.sevenwindsstudiotask.R
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentLoginBinding
import com.vladiyak.sevenwindsstudiotask.utils.TokenInstance
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding ?: throw RuntimeException("FragmentLoginBinding == null")

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            loginButton.setOnClickListener {
                logIn()
            }
            editTextPassword.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    logIn()
                    true
                } else {
                    false
                }
            }
            signUpButton.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
                findNavController().navigate(action)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiEvent.collect(::handleUiEvent)
                }
            }
        }
    }

    private fun logIn() {
        with(binding) {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            viewModel.logIn(email, password)
        }
    }

    private fun handleUiEvent(uiEvent: LoginUiEvent) {
        when (uiEvent) {
            LoginUiEvent.ErrorUnknown -> {
                showSnackBar(R.string.error_unknown)
            }

            LoginUiEvent.ErrorEmptyEmail -> {
                showSnackBar(R.string.error_empty_email)
            }

            LoginUiEvent.ErrorEmptyPassword -> {
                showSnackBar(R.string.error_empty_password)
            }

            LoginUiEvent.ErrorConnection -> {
                showSnackBar(R.string.error_connection)
            }

            LoginUiEvent.ErrorRequest -> {
                showSnackBar(R.string.error_request)
            }

            LoginUiEvent.ErrorInvalidCredentials -> {
                showSnackBar(R.string.error_invalid_credentials)
            }

            LoginUiEvent.NavigateToNearbyCoffeeShops -> {
                val navController = findNavController()
                navController.graph.setStartDestination(R.id.nearbyCoffeeShopsFragment)

                val action = LoginFragmentDirections.actionLoginFragmentToNearbyCoffeeShopsFragment()
                navController.navigate(action)
            }
        }
    }


    private fun showSnackBar(@StringRes resId: Int) {
        Snackbar.make(binding.root, resId, Snackbar.LENGTH_SHORT).show()
    }
}
