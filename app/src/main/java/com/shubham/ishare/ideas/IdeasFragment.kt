package com.shubham.ishare.ideas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shubham.ishare.CommonViewModel
import com.shubham.ishare.MainActivity
import com.shubham.ishare.R
import com.shubham.ishare.adapters.IdeaAdapter
import com.shubham.ishare.databinding.FragmentIdeasBinding
import com.shubham.ishare.user

class IdeasFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentIdeasBinding>(inflater, R.layout.fragment_ideas, container, false)

        //If user already exist then hide the login button
        user.observe(viewLifecycleOwner, Observer {
            if(it != null){
                binding.login.visibility = View.GONE
            } else {
                binding.login.visibility = View.VISIBLE
            }
        })

        binding.login.setOnClickListener{
            navigateToLoginFragment()
        }

        //Common ViewModel
        val commonViewModel = ViewModelProvider(requireActivity()).get(CommonViewModel::class.java)
        //Ideas ViewModel
        val viewModel = ViewModelProvider(this).get(IdeasViewModel::class.java)

        //Update Ideas ViewModel with ideas from common ViewModel
        commonViewModel.ideaResponse.observe(viewLifecycleOwner, Observer {
            viewModel.updateResponse(commonViewModel.ideaResponse.value)
        })

        //Recycler View Adapter
        val adapter = IdeaAdapter()
        binding.ideaList.adapter = adapter
        //Update data in recycler view whenever it changes
        viewModel.response.observe(viewLifecycleOwner, Observer {
            adapter.data = viewModel.response.value
        })

        //Set bottom navigation to visible in ideas fragment
        val bottomNavView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigation)
        bottomNavView.visibility = View.VISIBLE

        //Navigate to other fragments on click
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.toPostIdea -> {
                    navigateToPostIdeaFragment()
                }

                R.id.toProfile -> {
                    navigateToProfileFragment()
                }
            }
            true
        }

        val topAppBar: MaterialToolbar = requireActivity().findViewById(R.id.appBar)
        topAppBar.setOnMenuItemClickListener{
            when(it.itemId){
                R.id.logout -> {
                    commonViewModel.logout()
                    navigateToLoginFragment()
                }
            }
            true
        }

        return binding.root
    }

    private fun navigateToPostIdeaFragment(){
        this.findNavController().navigate(R.id.action_ideasFragment_to_postIdeaFragment)
    }

    private fun navigateToProfileFragment(){
        this.findNavController().navigate(R.id.action_ideasFragment_to_profileFragment)
    }

    private fun navigateToLoginFragment(){
        this.findNavController().navigate(R.id.action_ideasFragment_to_loginFragment)
    }
}