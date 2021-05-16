package com.shubham.ishare.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shubham.ishare.CommonViewModel
import com.shubham.ishare.R
import com.shubham.ishare.databinding.FragmentOpeningBinding
import com.shubham.ishare.ideasResponse
import com.shubham.ishare.user
import java.util.*

class OpeningFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentOpeningBinding>(inflater, R.layout.fragment_opening, container,false)

        val commonViewModel = ViewModelProvider(requireActivity()).get(CommonViewModel::class.java)

        val bottomNavView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigation)
        bottomNavView.visibility = View.GONE

        val topAppBar: MaterialToolbar = requireActivity().findViewById(R.id.appBar)
        topAppBar.menu.setGroupVisible(R.id.icons_group, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        user.observe(viewLifecycleOwner, androidx.lifecycle.Observer { it ->
            if(it != null)
                this.findNavController().navigate(R.id.action_openingFragment_to_ideasFragment)
            else
                this.findNavController().navigate(R.id.action_openingFragment_to_loginFragment)
        })
    }
}