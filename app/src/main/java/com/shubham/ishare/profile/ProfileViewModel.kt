package com.shubham.ishare.profile

import androidx.lifecycle.ViewModel

class ProfileViewModel: ViewModel() {
    private val yourIdeas = listOf<Int>(1, 2)
    private val likedIdeas = listOf<Int>(1, 2, 3, 4)

    var ideas: List<Int> = listOf()
    var heading: String = ""

    fun changeBottomSheetContentToYourIdeas(){
        heading = "Your Ideas"
        ideas = yourIdeas
    }

    fun changeBottomSheetContentToLikedIdeas(){
        heading = "Ideas You've Liked"
        ideas = likedIdeas
    }
}