package com.shubham.ishare.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.shubham.ishare.CommonViewModel
import com.shubham.ishare.R
import com.shubham.ishare.adapters.IdeaAdapter
import com.shubham.ishare.databinding.FragmentProfileBinding
import com.shubham.ishare.ideasResponse
import com.shubham.ishare.user
import java.util.*
import kotlin.concurrent.schedule

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(inflater, R.layout.fragment_profile, container, false)

        //Common ViewModel
        val commonViewModel = ViewModelProvider(requireActivity()).get(CommonViewModel::class.java)

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

        user.observe(viewLifecycleOwner, Observer {
            if(it == null){
                topAppBar.menu.findItem(R.id.logout).setIcon(R.drawable.ic_baseline_login_24)
                binding.username.text = getString(R.string.default_username)
                binding.userEmail.text = getString(R.string.default_email)
            } else {
                topAppBar.menu.findItem(R.id.logout).setIcon(R.drawable.logout)
                binding.username.text = user.value?.username
                binding.userEmail.text = user.value?.email
            }
        })

        //Profile ViewModel
        val viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        //Recycler View Adapter for posted and liked Ideas lists
        val ideasAdapter = IdeaAdapter(requireContext())
        binding.ideasList.adapter = ideasAdapter

        //Update data in profile ViewModel whenever a change occurs
        ideasResponse.observe(viewLifecycleOwner, Observer {
            viewModel.updateResponse(ideasResponse.value)
        })

        //Update data in recycler view adapter
        viewModel.response.observe(viewLifecycleOwner, Observer {
            viewModel.yourIdeas()
            viewModel.likedIdeas()
            ideasAdapter.submitList(viewModel.ideas)
        })

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        //CLick Listeners
        binding.apply {
            yourIdeasButton.setOnClickListener {
                if(viewModel.ideas == viewModel.yourIdeas()) {
                    changeBottomSheetState(bottomSheetBehavior, false)
                } else {
                    changeBottomSheetState(bottomSheetBehavior, true)
                    viewModel.changeBottomSheetContentToYourIdeas()
                    bottomSheetHeading.text = viewModel.heading
                    ideasAdapter.submitList(viewModel.ideas)
                }
            }

            likedIdeasButton.setOnClickListener {
                if(viewModel.ideas == viewModel.likedIdeas()) {
                    changeBottomSheetState(bottomSheetBehavior, false)
                } else {
                    changeBottomSheetState(bottomSheetBehavior, true)
                    viewModel.changeBottomSheetContentToLikedIdeas()
                    bottomSheetHeading.text = viewModel.heading
                    ideasAdapter.submitList(viewModel.ideas)
                }
            }
        }

        //Show bottom navigation on Profile fragment
        val bottomNavView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigation)
        bottomNavView.visibility = View.VISIBLE

        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.postIdeaFragment -> {
                    navigateToPostIdeaFragment()
                }

                R.id.ideasFragment -> {
                    navigateToIdeasFragment()
                }
            }
            true
        }

        return binding.root
    }

    private fun navigateToPostIdeaFragment(){
        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_postIdeaFragment)
    }

    private fun navigateToIdeasFragment(){
        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_ideasFragment)
    }

    private fun navigateToLoginFragment(){
        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_loginFragment)
    }

    //Change state of bottom sheet which is used to display posted and liked ideas
    private fun changeBottomSheetState(sheet: BottomSheetBehavior<LinearLayout>, changeState: Boolean){
        if(changeState){
            when(sheet.state){
                BottomSheetBehavior.STATE_COLLAPSED -> sheet.state = BottomSheetBehavior.STATE_EXPANDED
                else -> {
                    sheet.state = BottomSheetBehavior.STATE_COLLAPSED
                    Timer().schedule(500) {
                        sheet.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }
            }
        } else {
            when(sheet.state){
                BottomSheetBehavior.STATE_COLLAPSED -> sheet.state = BottomSheetBehavior.STATE_EXPANDED
                else -> sheet.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }
}