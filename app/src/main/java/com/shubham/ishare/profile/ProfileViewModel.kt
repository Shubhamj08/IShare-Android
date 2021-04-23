package com.shubham.ishare.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubham.ishare.ideas.Idea
import com.shubham.ishare.services.Backend
import com.shubham.ishare.user
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {

    //Variables for ideas objects
    private val _response = MutableLiveData<List<Idea>>()
    val response: LiveData<List<Idea>>
    get() = _response

    init{
        _response.value = listOf<Idea>()
    }

    fun updateResponse(ideas: List<Idea>?){
        _response.value = ideas
    }

    fun yourIdeas(): List<Idea>? {
        return _response.value?.filter {
            it.author == user.value?._id
        }
    }
    fun likedIdeas(): List<Idea>? {
        return _response.value?.filter {
            it.likes.contains(user.value?._id)
        }
    }

    var ideas: List<Idea>? = listOf<Idea>()
    var heading: String = ""

    fun changeBottomSheetContentToYourIdeas(){
        heading = "Your Ideas"
        ideas = yourIdeas()
    }

    fun changeBottomSheetContentToLikedIdeas(){
        heading = "Ideas You've Liked"
        ideas = likedIdeas()
    }
}