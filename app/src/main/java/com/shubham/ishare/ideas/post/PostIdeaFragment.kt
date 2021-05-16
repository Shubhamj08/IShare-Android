package com.shubham.ishare.ideas.post

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shubham.ishare.*
import com.shubham.ishare.databinding.FragmentPostIdeaBinding

class PostIdeaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPostIdeaBinding>(inflater, R.layout.fragment_post_idea, container, false)

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
            } else {
                topAppBar.menu.findItem(R.id.logout).setIcon(R.drawable.logout)
            }
        })


        commonViewModel.apply {
            postResponse.observe(viewLifecycleOwner, Observer {
                if(it){
                    postResponse.value = false
                    getIdeasFromBackend()
                }
            })

            gotResponse.observe(viewLifecycleOwner, Observer {
                if(it){
                    gotResponse.value = false
                    navigateToIdeasFragment()
                }
            })

            postError.observe(viewLifecycleOwner, Observer {
                if(it != null){
                    binding.progressBar.visibility = View.GONE
                    binding.submitButton.isEnabled = true
                    if(it.contains("401") || it.contains("NullPointerException")){
                        binding.titleContainer.error = "You need to login first"
                    } else
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                    postError.value = null
                }
            })

        }

        //PostIdea ViewModel
        val viewModel = ViewModelProvider(this).get(PostIdeaViewModel::class.java)
        binding.viewModel = viewModel

        //Observe containers for validation
        viewModel.apply {
            title.observe(viewLifecycleOwner, Observer {
                binding.titleContainer.error = titleError
            })

            description.observe(viewLifecycleOwner, Observer {
                binding.descriptionContainer.error = descriptionError
            })
        }

        //Click handler for idea submit button
        binding.submitButton.setOnClickListener {
            hideKeyboard()
            if(viewModel.onSubmit(binding.postTitle, binding.postDescription)){
                binding.progressBar.visibility = View.VISIBLE
                it.isEnabled = false
                commonViewModel.post(binding.postTitle.text.toString(), binding.postDescription.text.toString())
            }
        }

        //Show bottom navigation on PostIdea Fragment
        val bottomNavView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigation)
        bottomNavView.visibility = View.VISIBLE

        //Navigate
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            hideKeyboard()
            when(item.itemId){
                R.id.ideasFragment -> {
                    navigateToIdeasFragment()
                }

                R.id.profileFragment -> {
                    navigateToProfileFragment()
                }
            }
            true
        }

        return binding.root
    }

    private fun navigateToIdeasFragment(){
        Navigation.findNavController(requireView()).navigate(R.id.action_postIdeaFragment_to_ideasFragment)
    }

    private fun navigateToProfileFragment(){
        Navigation.findNavController(requireView()).navigate(R.id.action_postIdeaFragment_to_profileFragment)
    }

    private fun navigateToLoginFragment(){
        Navigation.findNavController(requireView()).navigate(R.id.action_postIdeaFragment_to_loginFragment)
    }

    private fun hideKeyboard(){
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }

}