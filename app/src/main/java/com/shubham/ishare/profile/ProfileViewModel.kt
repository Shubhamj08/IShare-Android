package com.shubham.ishare.profile

import androidx.lifecycle.ViewModel
import com.shubham.ishare.ideas.Idea
import com.shubham.ishare.ideas.iList

class ProfileViewModel: ViewModel() {
    val yourIdeas = iList.filter {
        !it.liked
    }
    val likedIdeas = iList.filter {
        it.liked
    }

    var ideas: List<Idea> = listOf<Idea>()
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