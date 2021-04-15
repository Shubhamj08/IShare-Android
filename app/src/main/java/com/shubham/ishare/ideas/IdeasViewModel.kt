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

    private val _response = MutableLiveData<List<Idea>>()
    val response: LiveData<List<Idea>>
    get() = _response

    init{
        getIdeasFromBackend()
        _response.value = listOf<Idea>()
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


}