package com.vladiyak.sevenwindsstudiotask.presentation.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import com.vladiyak.sevenwindsstudiotask.R
import com.vladiyak.sevenwindsstudiotask.data.models.signup.Token
import com.vladiyak.sevenwindsstudiotask.data.utils.TokenInstance
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentLoginBinding
import com.vladiyak.sevenwindsstudiotask.databinding.FragmentSignUpBinding
import com.vladiyak.sevenwindsstudiotask.presentation.signup.SignUpFragment


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding ?: throw RuntimeException("FragmentLoginBinding == null")

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

        val myToken = token.getToken()

        binding.loginButton.setOnClickListener {
            Log.d("Proverka tokena", "Token $myToken")
        }

    }
}