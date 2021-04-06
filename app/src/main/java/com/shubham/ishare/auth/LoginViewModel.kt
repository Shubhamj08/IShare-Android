package com.shubham.ishare.auth

import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shubham.ishare.validation.Validate

class LoginViewModel: ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    init {
        Log.i("All Logs", "Login ViewModel Created")
//        _email.value = ""
//        _password.value = ""
    }

    fun onSubmit(em: EditText, pass: EditText){
        _email.value = em.text.toString()
        _password.value = pass.text.toString()
    }

    private val validate = Validate()

    fun validateEmail(): String? {
        return validate.validateEmail(_email.value.toString())
    }

    fun validatePassword(): String? {
        return validate.validatePassword(_password.value.toString())
    }
}