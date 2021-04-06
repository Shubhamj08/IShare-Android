package com.shubham.ishare.auth

import android.util.Log
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shubham.ishare.validation.Validate

class RegisterViewModel: ViewModel() {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword: LiveData<String>
        get() = _confirmPassword

    init {
        Log.i("All Logs", "Login ViewModel Created")
//        _username.value = ""
//        _email.value = ""
//        _password.value = ""
//        _confirmPassword.value = ""
    }

    fun onSubmit(user: EditText, em: EditText, pass: EditText, confirmPass: EditText){
        _username.value = user.text.toString()
        _email.value = em.text.toString()
        _password.value = pass.text.toString()
        _confirmPassword.value = confirmPass.text.toString()
    }

    private val validate = Validate()

    fun validateUsername(): String? {
        return validate.validateUsername(_username.value.toString())
    }

    fun validateEmail(): String? {
        return validate.validateEmail(_email.value.toString())
    }

    fun validatePassword(): String? {
        return validate.validatePassword(_password.value.toString())
    }

    fun validateConfirmPassword(): String? {
        return validate.validateConfirmPassword(_password.value.toString(), _confirmPassword.value.toString())
    }
}