package com.shubham.ishare

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.shubham.ishare.auth.LoginCreds
import com.shubham.ishare.database.User
import com.shubham.ishare.database.UserDao
import com.shubham.ishare.database.UserDatabase
import com.shubham.ishare.ideas.Idea
import com.shubham.ishare.services.Backend
import com.shubham.ishare.services.JWTUtils
import kotlinx.coroutines.launch

var user = MutableLiveData<User?>()
class CommonViewModel(application: Application): AndroidViewModel(application) {

    private val _ideasResponse = MutableLiveData<List<Idea>>()
    val ideaResponse: LiveData<List<Idea>>
    get() = _ideasResponse

    private val loginResponse: User? = null

    private val jwt: String = ""
    val database = UserDatabase.getInstance(application).dao

    init{
        _ideasResponse.value = listOf<Idea>()
        getIdeasFromBackend()
        checkAndGetUser()
    }

    private fun checkAndGetUser(){
        viewModelScope.launch {
            user.value = getUserFromDatabase()
            Log.i("database", user.value.toString())
        }
    }

    private suspend fun getUserFromDatabase(): User?{
        return database.get()
    }

    private suspend fun addUserToDb(){
        database.insert(loginResponse!!)
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
                val loginResponse = JWTUtils().decoded(data.jsonWebToken)
                addUserToDb()
                Log.i("login", user.value.toString())
            } catch (ex: Exception){
                Log.i("login", "Failed: ${ex.message}")
            }
        }
    }

}