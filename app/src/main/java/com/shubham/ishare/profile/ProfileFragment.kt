package com.shubham.ishare.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shubham.ishare.R
import com.shubham.ishare.adapters.IdeaAdapter
import com.shubham.ishare.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(inflater, R.layout.fragment_profile, container, false)

        val viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        val ideasAdapter = IdeaAdapter()
        binding.ideasList.adapter = ideasAdapter
        ideasAdapter.data = listOf()

        binding.apply {
            yourIdeasButton.setOnClickListener {
                viewModel.changeBottomSheetContentToYourIdeas()
                bottomSheetHeading.text = viewModel.heading
                ideasAdapter.data = viewModel.ideas
            }

            likedIdeasButton.setOnClickListener {
                viewModel.changeBottomSheetContentToLikedIdeas()
                bottomSheetHeading.text = viewModel.heading
                ideasAdapter.data = viewModel.ideas
            }
        }

        val bottomNavView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigation)
        bottomNavView.visibility = View.VISIBLE
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.toPostIdea -> {
                    navigateToPostIdeaFragment()
                }

                R.id.toIdeas -> {
                    navigateToIdeasFragment()
                }
            }
            true
        }

        return binding.root
    }

    private fun navigateToPostIdeaFragment(){
        this.findNavController().navigate(R.id.action_profileFragment_to_postIdeaFragment)
    }

    private fun navigateToIdeasFragment(){
        this.findNavController().navigate(R.id.action_profileFragment_to_ideasFragment)
    }
}