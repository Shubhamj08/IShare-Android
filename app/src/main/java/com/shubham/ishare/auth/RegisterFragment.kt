package com.shubham.ishare.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shubham.ishare.CommonViewModel
import com.shubham.ishare.R
import com.shubham.ishare.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentRegisterBinding>(inflater, R.layout.fragment_register, container, false)

        //Common VieModel
        val commonViewModel: CommonViewModel = ViewModelProvider(requireActivity()).get(CommonViewModel::class.java)

        commonViewModel.apply {
            registerResponse.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    registerResponse.value = null
                    navigateToLoginFragment()
                }
            })

            registerError.observe(viewLifecycleOwner, Observer{
                if(it != null){
                    binding.emailContainer.error = "user with this email already registered"
                    binding.progressBar.visibility = View.GONE
                    registerError.value = null
                }
            })
        }

        //Register ViewModel
        val viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        binding.registerViewModel = viewModel

        //Click Listeners
        binding.apply {
            submitButton.setOnClickListener {
                hideKeyboard()
                emailContainer.error = null
                progressBar.visibility = View.VISIBLE
                if(viewModel.onSubmit(usernameText, emailText, passwordText, confirmPasswordText)){
                    commonViewModel.register(
                        usernameText.text.toString(),
                        emailText.text.toString(),
                        passwordText.text.toString()
                    )
                } else {
                    progressBar.visibility = View.GONE
                }
            }

            gotoLoginButton.setOnClickListener { view: View ->
                navigateToLoginFragment()
            }
        }

        //observers of fields for validation
        viewModel.apply{
            username.observe(viewLifecycleOwner, Observer {
                binding.usernameContainer.error = usernameError
            })
            email.observe(viewLifecycleOwner, Observer {
                binding.emailContainer.error = emailError
            })

            password.observe(viewLifecycleOwner, Observer {
                binding.passwordContainer.error = passError
            })

            confirmPassword.observe(viewLifecycleOwner, Observer {
                binding.confirmPasswordContainer.error = confirmPassError
            })
        }

        //Hide Bottom navigation on register fragment
        val bottomNavView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigation)
        bottomNavView.visibility = View.GONE

        val topAppBar: MaterialToolbar = requireActivity().findViewById(R.id.appBar)
        topAppBar.menu.setGroupVisible(R.id.icons_group, false)

        return binding.root
    }

    private fun navigateToLoginFragment(){
        this.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }

    private fun hideKeyboard(){
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }

}