package com.vladiyak.sevenwindsstudiotask.presentation.signup

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
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding ?: throw RuntimeException("FragmentSignUpBinding == null")

    private val viewModel by viewModels<SignUpViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            signUpButton.setOnClickListener {
                signUp()
            }
            signUpToken.setOnClickListener {
                val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
                findNavController().navigate(action)
            }

            editTextRepeatPassword.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    signUp()
                    true
                } else {
                    false
                }
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

    private fun handleUiEvent(uiEvent: SignUpUiEvent) {
        when (uiEvent) {
            SignUpUiEvent.ErrorUnknown -> {
                showSnackBar(R.string.error_unknown)
            }

            SignUpUiEvent.ErrorInvalidEmail -> {
                showSnackBar(R.string.error_invalid_email)
            }

            SignUpUiEvent.ErrorEmptyPassword -> {
                showSnackBar(R.string.error_empty_password)
            }

            SignUpUiEvent.ErrorPasswordsDoNotMatch -> {
                showSnackBar(R.string.error_passwords_do_not_match)
            }

            SignUpUiEvent.ErrorConnection -> {
                showSnackBar(R.string.error_connection)
            }

            SignUpUiEvent.ErrorRequest -> {
                showSnackBar(R.string.error_request)
            }

            SignUpUiEvent.ErrorAccountIsTaken -> {
                showSnackBar(R.string.error_account_is_taken)
            }

            SignUpUiEvent.NavigateToNearbyCoffeeShops -> {
                val navController = findNavController()
                navController.graph.setStartDestination(R.id.nearbyCoffeeShopsFragment)

                val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
                navController.navigate(action)
            }
        }
    }

    private fun signUp() {
        with(binding) {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val passwordConfirmation = editTextRepeatPassword.text.toString()
            viewModel.signUp(email, password, passwordConfirmation)
        }
    }

    private fun showSnackBar(@StringRes resId: Int) {
        Snackbar.make(binding.root, resId, Snackbar.LENGTH_SHORT).show()
    }


}