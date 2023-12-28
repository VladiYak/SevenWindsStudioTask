package com.vladiyak.sevenwindsstudiotask.presentation.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.vladiyak.sevenwindsstudiotask.R
import com.vladiyak.sevenwindsstudiotask.data.models.signup.Token
import com.vladiyak.sevenwindsstudiotask.data.models.signup.User
import com.vladiyak.sevenwindsstudiotask.utils.TokenInstance
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentSignUpBinding
import com.vladiyak.sevenwindsstudiotask.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding ?: throw RuntimeException("FragmentSignUpBinding == null")

    private val viewModel by viewModels<SignUpViewModel>()
    private val tokenInstance = TokenInstance


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpButton.setOnClickListener {
            val login = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val user = User(login, password)
            viewModel.signUp(user)
        }

        viewModel.token.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    tokenInstance.addToken(response.data)
                    Snackbar.make(view, getString(R.string.sign_up_success), Snackbar.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {
                    Snackbar.make(view, getString(R.string.sign_up_error), Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        binding.signUpToken.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }


}