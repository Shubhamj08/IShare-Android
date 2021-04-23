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
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.shubham.ishare.CommonViewModel
import com.shubham.ishare.R
import com.shubham.ishare.adapters.IdeaAdapter
import com.shubham.ishare.databinding.FragmentProfileBinding
import com.shubham.ishare.user
import java.util.*
import kotlin.concurrent.schedule

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(inflater, R.layout.fragment_profile, container, false)

        //Hide Login if user already exists
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
        //Profile ViewModel
        val viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        //Recycler View Adapter for posted and liked Ideas lists
        val ideasAdapter = IdeaAdapter()
        binding.ideasList.adapter = ideasAdapter
        ideasAdapter.data = listOf()

        //Update data in profile ViewModel whenever a change occurs
        commonViewModel.ideaResponse.observe(viewLifecycleOwner, Observer {
            viewModel.updateResponse(commonViewModel.ideaResponse.value)
        })

        //Update data in recycler view adapter
        viewModel.response.observe(viewLifecycleOwner, Observer {
            viewModel.yourIdeas()
            viewModel.likedIdeas()
            ideasAdapter.data = viewModel.ideas
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
                    ideasAdapter.data = viewModel.ideas
                }
            }

            likedIdeasButton.setOnClickListener {
                if(viewModel.ideas == viewModel.likedIdeas()) {
                    changeBottomSheetState(bottomSheetBehavior, false)
                } else {
                    changeBottomSheetState(bottomSheetBehavior, true)
                    viewModel.changeBottomSheetContentToLikedIdeas()
                    bottomSheetHeading.text = viewModel.heading
                    ideasAdapter.data = viewModel.ideas
                }
            }
        }

        //Show bottom navigation on Profile fragment
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
        this.findNavController().navigate(R.id.action_profileFragment_to_postIdeaFragment)
    }

    private fun navigateToIdeasFragment(){
        this.findNavController().navigate(R.id.action_profileFragment_to_ideasFragment)
    }

    private fun navigateToLoginFragment(){
        this.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
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