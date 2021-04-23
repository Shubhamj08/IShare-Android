package com.shubham.ishare.ideas

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubham.ishare.services.Backend
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IdeasViewModel: ViewModel() {

    //Variables for idea objects
    private val _response = MutableLiveData<List<Idea>>()
    val response: LiveData<List<Idea>>
    get() = _response

    init{
        _response.value = listOf<Idea>()
    }

    //update Ideas in the ViewModel
    fun updateResponse(ideas: List<Idea>?){
        _response.value = ideas
    }
}