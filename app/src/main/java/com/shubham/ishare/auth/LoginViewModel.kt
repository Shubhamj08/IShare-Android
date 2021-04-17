package com.shubham.ishare.auth

import android.util.Log
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubham.ishare.services.Backend
import com.shubham.ishare.validation.AuthValidate
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    var emailError: String? = null
    var passError: String? = null

    fun onSubmit(em: EditText, pass: EditText): Boolean{
        _email.value = em.text.toString()
        _password.value = pass.text.toString()
        emailError = validateEmail()
        passError = validatePassword()

        if(emailError == null && passError == null){
            return true
        }

        return false
    }

    private val validate = AuthValidate()

    fun validateEmail(): String? {
        return validate.validateEmail(_email.value.toString())
    }

    fun validatePassword(): String? {
        return validate.validatePassword(_password.value.toString())
    }
}