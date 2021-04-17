package com.shubham.ishare

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubham.ishare.auth.LoginCreds
import com.shubham.ishare.ideas.Idea
import com.shubham.ishare.services.Backend
import com.shubham.ishare.services.JWTUtils
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommonViewModel: ViewModel() {

    private val _ideasResponse = MutableLiveData<List<Idea>>()
    val ideaResponse: LiveData<List<Idea>>
    get() = _ideasResponse

    private val jwt: String = ""

    init{
        _ideasResponse.value = listOf<Idea>()
        getIdeasFromBackend()
    }
    private fun getIdeasFromBackend(){
        viewModelScope.launch {
            try {
                _ideasResponse.value = Backend.retrofitService.getProperties()
            } catch (ex: Exception){
                Log.i("backend", "Failed: ${ex.message}")
            }
        }
    }

    fun login(email: String, password: String){
        viewModelScope.launch {
            val creds = LoginCreds(email, password)
            try {
                val data = Backend.retrofitService.login(creds)
                JWTUtils().decoded(data.jsonWebToken)
            } catch (ex: Exception){
                Log.i("login", "Failed: ${ex.message}")
            }
        }
    }

}