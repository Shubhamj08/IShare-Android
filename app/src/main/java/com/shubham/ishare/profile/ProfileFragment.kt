package com.shubham.ishare.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.shubham.ishare.R
import com.shubham.ishare.adapters.IdeaAdapter
import com.shubham.ishare.databinding.FragmentProfileBinding
import java.util.*
import kotlin.concurrent.schedule

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

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        binding.apply {
            yourIdeasButton.setOnClickListener {
                if(viewModel.ideas == viewModel.yourIdeas) {
                    changeBottomSheetState(bottomSheetBehavior, false)
                } else {
                    changeBottomSheetState(bottomSheetBehavior, true)
                    viewModel.changeBottomSheetContentToYourIdeas()
                    bottomSheetHeading.text = viewModel.heading
                    ideasAdapter.data = viewModel.ideas
                }
            }

            likedIdeasButton.setOnClickListener {
                if(viewModel.ideas == viewModel.likedIdeas) {
                    changeBottomSheetState(bottomSheetBehavior, false)
                } else {
                    changeBottomSheetState(bottomSheetBehavior, true)
                    viewModel.changeBottomSheetContentToLikedIdeas()
                    bottomSheetHeading.text = viewModel.heading
                    ideasAdapter.data = viewModel.ideas
                }
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