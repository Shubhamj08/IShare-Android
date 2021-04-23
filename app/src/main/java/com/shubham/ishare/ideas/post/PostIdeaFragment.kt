package com.shubham.ishare.ideas.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shubham.ishare.CommonViewModel
import com.shubham.ishare.R
import com.shubham.ishare.databinding.FragmentPostIdeaBinding
import com.shubham.ishare.user

class PostIdeaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPostIdeaBinding>(inflater, R.layout.fragment_post_idea, container, false)

        //If user exists then hide the visibility
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

        //PostIdea ViewModel
        val viewModel = ViewModelProvider(this).get(PostIdeaViewModel::class.java)
        binding.viewModel = viewModel

        //Observe containers for validation
        viewModel.apply {
            title.observe(viewLifecycleOwner, Observer {
                binding.titleContainer.error = validateTitle()
            })

            description.observe(viewLifecycleOwner, Observer {
                binding.descriptionContainer.error = validateDescription()
            })
        }

        //Click handler for idea submit button
        binding.submitButton.setOnClickListener {
            viewModel.onSubmit(binding.postTitle, binding.postDescription)
        }

        //Show bottom navigation on PostIdea Fragment
        val bottomNavView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigation)
        bottomNavView.visibility = View.VISIBLE

        //Navigate
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.toIdeas -> {
                    navigateToIdeasFragment()
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

    private fun navigateToIdeasFragment(){
        this.findNavController().navigate(R.id.action_postIdeaFragment_to_ideasFragment)
    }

    private fun navigateToProfileFragment(){
        this.findNavController().navigate(R.id.action_postIdeaFragment_to_profileFragment)
    }

    private fun navigateToLoginFragment(){
        this.findNavController().navigate(R.id.action_ideasFragment_to_loginFragment)
    }

}