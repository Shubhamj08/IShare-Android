package com.shubham.ishare.auth

import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    init {
        Log.i("All Logs", "Login ViewModel Created")
        _email.value = ""
        _password.value = ""
    }

    fun onSubmit(login: EditText, pass: EditText){
        _email.value = login.text.toString()
        _password.value = pass.text.toString()
    }

    fun validateEmail(emailText: String): String{
        return ""
    }

    fun validatePassword(passwordText: String): String{
        return ""
    }
}