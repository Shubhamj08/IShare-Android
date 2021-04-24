package com.shubham.ishare

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.shubham.ishare.auth.LoginCreds
import com.shubham.ishare.auth.RegisterCreds
import com.shubham.ishare.database.User
import com.shubham.ishare.database.UserDatabase
import com.shubham.ishare.ideas.Idea
import com.shubham.ishare.ideas.post.Post
import com.shubham.ishare.services.Backend
import com.shubham.ishare.services.JWTUtils
import com.shubham.ishare.services.JsonWebToken
import kotlinx.coroutines.launch

var user = MutableLiveData<User?>()

//Variables for working with ideas response from backend
val ideasResponse = MutableLiveData<List<Idea>>()

var jwt: String? = null

val gotResponse = MutableLiveData<Boolean>()

class CommonViewModel(application: Application): AndroidViewModel(application) {

    //Variables for working with user object
    private var loginResponse: User? = null
    var loginError = MutableLiveData<String>()
    private var token: JsonWebToken? = null

    val database = UserDatabase.getInstance(application).dao

    //Variable for registration work
    var registerResponse = MutableLiveData<Object?>()
    var registerError = MutableLiveData<String>()

    //Variables for posting idea
    var postResponse = MutableLiveData<Boolean>()
    var postError = MutableLiveData<String>()

    init{
        ideasResponse.value = listOf<Idea>()
        checkAndGetUser()
        getIdeasFromBackend()
    }

    //function to whether the user exists when activity starts
    private fun checkAndGetUser(){
        viewModelScope.launch {
            user.value = getUserFromDatabase()
            jwt = getTokenFromDb()
        }
    }

    private suspend fun getUserFromDatabase(): User?{
        return database.get()
    }

    private suspend fun getTokenFromDb(): String? {
        return database.getToken()?.jsonWebToken
    }

    private suspend fun addUserToDb(){
        database.insert(loginResponse!!)
        database.insertToken(token!!)
        checkAndGetUser()
    }

    private suspend fun removeUserFromDb(){
        database.clear()
        database.clearToken()
    }


    // function to get ideas from backend at the start of the activity
    fun getIdeasFromBackend(){
        viewModelScope.launch {
            try {
                ideasResponse.value = Backend.retrofitService.getProperties()
                gotResponse.value = true
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
                token = data
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

    // function to register a user
    fun logout(){
        viewModelScope.launch {
            removeUserFromDb()
            jwt = ""
        }
        user.value = null
    }

    fun post(title: String, desc: String){
        viewModelScope.launch {
            val postInfo = Post(title, desc)
            try {
                Backend.retrofitService.postIdea(jwt!!, postInfo)
                postResponse.value = true
                Log.i("post", "${postResponse.value}")
            } catch (ex: Exception){
                postError.value = ex.toString()
                Log.i("post", "Failed: $ex")
            }
        }
    }

}