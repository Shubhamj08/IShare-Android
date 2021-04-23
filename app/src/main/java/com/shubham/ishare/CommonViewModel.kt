package com.shubham.ishare

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.shubham.ishare.auth.LoginCreds
import com.shubham.ishare.auth.RegisterCreds
import com.shubham.ishare.database.User
import com.shubham.ishare.database.UserDatabase
import com.shubham.ishare.ideas.Idea
import com.shubham.ishare.services.Backend
import com.shubham.ishare.services.JWTUtils
import kotlinx.coroutines.launch

var user = MutableLiveData<User?>()
class CommonViewModel(application: Application): AndroidViewModel(application) {

    //Variables for working with user object
    private var loginResponse: User? = null
    var loginError = MutableLiveData<String>()

    private val jwt: String = ""
    val database = UserDatabase.getInstance(application).dao

    //Variable for registration work
    var registerResponse = MutableLiveData<Object?>()
    var registerError = MutableLiveData<String>()

    //Variables for working with ideas response from backend
    private val _ideasResponse = MutableLiveData<List<Idea>>()
    val ideaResponse: LiveData<List<Idea>>
        get() = _ideasResponse

    init{
        _ideasResponse.value = listOf<Idea>()
        checkAndGetUser()
        getIdeasFromBackend()
    }

    //function to whether the user exists when activity starts
    private fun checkAndGetUser(){
        viewModelScope.launch {
            user.value = getUserFromDatabase()
        }
    }

    private suspend fun getUserFromDatabase(): User?{
        return database.get()
    }

    private suspend fun addUserToDb(){
        database.insert(loginResponse!!)
        checkAndGetUser()
    }

    private suspend fun removeUserFromDb(){
        database.clear()
    }


    // function to get ideas from backend at the start of the activity
    private fun getIdeasFromBackend(){
        viewModelScope.launch {
            try {
                _ideasResponse.value = Backend.retrofitService.getProperties()
            } catch (ex: Exception){
                Log.i("backend", "Failed: ${ex.message}")
            }
        }
    }

    // function to login a user
    fun login(email: String, password: String){
        viewModelScope.launch {
            val creds = LoginCreds(email, password)
            try {
                val data = Backend.retrofitService.login(creds)
                loginResponse = JWTUtils().decoded(data.jsonWebToken)
                addUserToDb()
                Log.i("login", user.value.toString())
            } catch (ex: Exception){
                loginError.value = ex.message.toString()
                Log.i("login", "Failed: $ex")
            }
        }
    }

    // function to register a user
    fun register(username: String, email: String, password: String){
        viewModelScope.launch {
            val creds = RegisterCreds(email, password, username)
            try {
                registerResponse.value = Backend.retrofitService.register(creds)
            } catch (ex: Exception){
                registerError.value = ex.message.toString()
                Log.i("register", "Failed: $ex")
            }
        }

        Log.i("register", "response: " + registerResponse.value.toString())
    }

    fun logout(){
        viewModelScope.launch {
            removeUserFromDb()
        }
        user.value = null
    }

}