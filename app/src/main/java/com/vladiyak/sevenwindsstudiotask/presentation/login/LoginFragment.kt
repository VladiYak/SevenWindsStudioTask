package com.vladiyak.sevenwindsstudiotask.presentation.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import com.vladiyak.sevenwindsstudiotask.R
import com.vladiyak.sevenwindsstudiotask.data.models.signup.Token
import com.vladiyak.sevenwindsstudiotask.data.models.signup.User
import com.vladiyak.sevenwindsstudiotask.utils.TokenInstance
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentLoginBinding
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentSignUpBinding
import com.vladiyak.sevenwindsstudiotask.presentation.signup.SignUpFragment
import com.vladiyak.sevenwindsstudiotask.presentation.signup.SignUpViewModel
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

            viewModel.token.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    val action =
                        LoginFragmentDirections.actionLoginFragmentToNearbyCoffeeShopsFragment(
                            it
                        )
                    findNavController().navigate(action)
                }
            })
        }
    }
}