package com.shubham.ishare.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.shubham.ishare.R
import com.shubham.ishare.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login, container, false)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.loginViewModel = viewModel

        binding.apply{
            submitButton.setOnClickListener {
                viewModel.onSubmit(loginText, passwordText)
            }

            gotoRegisterButton.setOnClickListener { view: View ->
                view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

            skipToIdeas.setOnClickListener { view: View ->
                view.findNavController().navigate(R.id.action_loginFragment_to_ideasFragment)
            }
        }

        viewModel.email.observe(viewLifecycleOwner, Observer {
            binding.emailContainer.error = viewModel.validateEmail(it)
        })
        viewModel.password.observe(viewLifecycleOwner, Observer {
            binding.passwordContainer.error = viewModel.validatePassword(it)
        })


        return binding.root
    }

}