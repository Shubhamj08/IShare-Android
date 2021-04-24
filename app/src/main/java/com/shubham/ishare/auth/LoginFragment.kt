package com.shubham.ishare.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shubham.ishare.CommonViewModel
import com.shubham.ishare.R
import com.shubham.ishare.databinding.FragmentLoginBinding
import com.shubham.ishare.user

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login, container, false)

        //Common ViewModel
        val commonViewModel = ViewModelProvider(requireActivity()).get(CommonViewModel::class.java)
        //if user already exists then navigate to ideas fragment
        user.observe(viewLifecycleOwner, Observer {
            if(it != null){
                this.view?.findNavController()?.navigate(R.id.action_loginFragment_to_ideasFragment)
            }
        })

        commonViewModel.loginError.observe(viewLifecycleOwner, Observer {
            if(it != null){
                binding.emailContainer.error = "email or password incorrect"
                binding.progressBar.visibility = View.GONE
                commonViewModel.loginError.value = null
            }
        })

        //Login ViewModel
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.loginViewModel = viewModel

        //Observers for email and password field
        viewModel.apply{
            email.observe(viewLifecycleOwner, Observer {
                binding.emailContainer.error = emailError
            })
            password.observe(viewLifecycleOwner, Observer {
                binding.passwordContainer.error = passError
            })
        }

        //Click listeners for buttons on layout page
        binding.apply{
            submitButton.setOnClickListener {
                hideKeyboard()
                emailContainer.error = null
                progressBar.visibility = View.VISIBLE
                if(viewModel.onSubmit(emailText, passwordText)){
                    commonViewModel.login(emailText.text.toString(), passwordText.text.toString())
                } else {
                    progressBar.visibility = View.GONE
                }
            }

            gotoRegisterButton.setOnClickListener { view: View ->
                view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

            skipToIdeas.setOnClickListener { view: View ->
                hideKeyboard()
                view.findNavController().navigate(R.id.action_loginFragment_to_ideasFragment)
            }
        }

        //Hide bottom navigation on login page
        val bottomNavView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigation)
        bottomNavView.visibility = View.GONE

        val topAppBar: MaterialToolbar = requireActivity().findViewById(R.id.appBar)
        topAppBar.menu.setGroupVisible(R.id.icons_group, false)

        return binding.root
    }

    private fun hideKeyboard(){
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }


}