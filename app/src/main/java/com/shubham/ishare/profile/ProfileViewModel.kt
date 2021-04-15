package com.shubham.ishare.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubham.ishare.ideas.Idea
import com.shubham.ishare.services.Backend
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {

    private val _response = MutableLiveData<List<Idea>>()
    val response: LiveData<List<Idea>>
    get() = _response

    init{
        _response.value = listOf<Idea>()
        getIdeasFromBackend()
    }

    private fun getIdeasFromBackend(){
        viewModelScope.launch {
            try {
                _response.value = Backend.retrofitService.getProperties()
            } catch (ex: Exception){
                Log.i("backend", "Failed: ${ex.message}")
            }
        }
    }

    fun yourIdeas(): List<Idea>? {
        return _response.value?.filter {
            !it.liked
        }
    }
    fun likedIdeas(): List<Idea>? {
        return _response.value?.filter {
            it.liked
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