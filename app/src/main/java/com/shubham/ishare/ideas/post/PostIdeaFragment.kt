package com.shubham.ishare.ideas.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shubham.ishare.R
import com.shubham.ishare.databinding.FragmentPostIdeaBinding

class PostIdeaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPostIdeaBinding>(inflater, R.layout.fragment_post_idea, container, false)

        val bottomNavView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigation)
        bottomNavView.visibility = View.VISIBLE
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

        return binding.root
    }

    private fun navigateToIdeasFragment(){
        this.findNavController().navigate(R.id.action_postIdeaFragment_to_ideasFragment)
    }

    private fun navigateToProfileFragment(){
        this.findNavController().navigate(R.id.action_postIdeaFragment_to_profileFragment)
    }

}