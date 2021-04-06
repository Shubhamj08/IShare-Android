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
import com.shubham.ishare.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentRegisterBinding>(inflater, R.layout.fragment_register, container, false)

        val viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        binding.registerViewModel = viewModel

        binding.apply {
            submitButton.setOnClickListener {
                viewModel.onSubmit(usernameText, emailText, passwordText, confirmPasswordText)
            }

            gotoLoginButton.setOnClickListener { view: View ->
                view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }

        viewModel.apply{
            username.observe(viewLifecycleOwner, Observer {
                binding.usernameContainer.error = validateUsername()
            })
            email.observe(viewLifecycleOwner, Observer {
                binding.emailContainer.error = validateEmail()
            })

            password.observe(viewLifecycleOwner, Observer {
                binding.passwordContainer.error = validatePassword()
            })

            confirmPassword.observe(viewLifecycleOwner, Observer {
                binding.confirmPasswordContainer.error = validateConfirmPassword()
            })
        }

        return binding.root
    }


}