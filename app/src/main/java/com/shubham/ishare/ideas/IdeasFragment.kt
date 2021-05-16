package com.shubham.ishare.ideas

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shubham.ishare.*
import com.shubham.ishare.adapters.IdeaAdapter
import com.shubham.ishare.databinding.FragmentIdeasBinding

class IdeasFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentIdeasBinding>(inflater, R.layout.fragment_ideas, container, false)

        //Common ViewModel
        val commonViewModel = ViewModelProvider(requireActivity()).get(CommonViewModel::class.java)

        //Recycler View Adapter
        val adapter = IdeaAdapter(requireContext())
        binding.ideaList.adapter = adapter

        val topAppBar: MaterialToolbar = requireActivity().findViewById(R.id.appBar)
        topAppBar.menu.setGroupVisible(R.id.icons_group, true)
        topAppBar.setOnMenuItemClickListener{
            when(it.itemId){
                R.id.logout -> {
                    commonViewModel.logout()
                    navigateToLoginFragment()
                }
            }
            true
        }
//        (topAppBar.menu.findItem(R.id.app_bar_search).actionView as SearchView).setOnQueryTextListener(
//            object : SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(p0: String?): Boolean {
//                    return false
//                }
//
//                override fun onQueryTextChange(p0: String?): Boolean {
//                    adapter.filter.filter(p0)
//                    return false
//                }
//
//            }
//        )

        user.observe(viewLifecycleOwner, Observer {
            if(it == null){
                topAppBar.menu.findItem(R.id.logout).setIcon(R.drawable.ic_baseline_login_24)
            } else {
                topAppBar.menu.findItem(R.id.logout).setIcon(R.drawable.logout)
            }
        })

        gotResponse.observe(viewLifecycleOwner, Observer {
            if(it) {
                gotResponse.value = false
            }
        })


        //Ideas ViewModel
        val viewModel = ViewModelProvider(this).get(IdeasViewModel::class.java)

        //Update Ideas ViewModel with ideas from common ViewModel
        ideasResponse.observe(viewLifecycleOwner, Observer {
            if(it.isEmpty())
                binding.progressBar.visibility = View.VISIBLE
            else {
                binding.progressBar.visibility = View.GONE
                adapter.submitList(it)
            }
        })

        //Set bottom navigation to visible in ideas fragment
        val bottomNavView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigation)
        bottomNavView.visibility = View.VISIBLE

        //Navigate to other fragments on click
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.postIdeaFragment -> {
                    navigateToPostIdeaFragment()
                }

                R.id.profileFragment -> {
                    navigateToProfileFragment()
                }
            }
            true
        }

        val onBackPressedCallback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(user.value != null)
                    activity?.finishAndRemoveTask()
                else
                    Navigation.findNavController(requireView()).popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

        return binding.root
    }

    private fun navigateToPostIdeaFragment(){
        Navigation.findNavController(requireView()).navigate(R.id.action_ideasFragment_to_postIdeaFragment)
    }

    private fun navigateToProfileFragment(){
        Navigation.findNavController(requireView()).navigate(R.id.action_ideasFragment_to_profileFragment)
    }

    private fun navigateToLoginFragment(){
        Navigation.findNavController(requireView()).navigate(R.id.action_ideasFragment_to_loginFragment)
    }
}