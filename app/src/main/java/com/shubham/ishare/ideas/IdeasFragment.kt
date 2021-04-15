package com.shubham.ishare.ideas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shubham.ishare.R
import com.shubham.ishare.adapters.IdeaAdapter
import com.shubham.ishare.databinding.FragmentIdeasBinding

class IdeasFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentIdeasBinding>(inflater, R.layout.fragment_ideas, container, false)

        val viewModel = ViewModelProvider(this).get(IdeasViewModel::class.java)

        val adapter = IdeaAdapter()
        binding.ideaList.adapter = adapter
        viewModel.response.observe(viewLifecycleOwner, Observer {
            adapter.data = viewModel.response.value
        })

        val bottomNavView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigation)
        bottomNavView.visibility = View.VISIBLE
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

        return binding.root
    }

    private fun navigateToPostIdeaFragment(){
        this.findNavController().navigate(R.id.action_ideasFragment_to_postIdeaFragment)
    }

    private fun navigateToProfileFragment(){
        this.findNavController().navigate(R.id.action_ideasFragment_to_profileFragment)
    }
}