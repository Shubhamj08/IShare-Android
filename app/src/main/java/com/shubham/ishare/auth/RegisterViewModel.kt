package com.shubham.ishare.auth

import android.util.Log
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shubham.ishare.validation.AuthValidate

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

    var usernameError: String? = null
    var emailError: String? = null
    var passError: String? = null
    var confirmPassError: String? = null

    fun onSubmit(user: EditText, em: EditText, pass: EditText, confirmPass: EditText): Boolean{
        usernameError = validateUsername(user.text.toString())
        emailError = validateEmail(em.text.toString())
        passError = validatePassword(pass.text.toString())
        confirmPassError = validateConfirmPassword(pass.text.toString(), confirmPass.text.toString())

        _username.value = user.text.toString()
        _email.value = em.text.toString()
        _password.value = pass.text.toString()
        _confirmPassword.value = confirmPass.text.toString()

        if(usernameError == null && emailError == null && passError == null && confirmPassError == null){
            return true
        }

        return false
    }

    private val validate = AuthValidate()

    fun validateUsername(user: String): String? {
        return validate.validateUsername(user)
    }

    fun validateEmail(em: String): String? {
        return validate.validateEmail(em)
    }

    fun validatePassword(pass: String): String? {
        return validate.validatePassword(pass)
    }

    fun validateConfirmPassword(pass: String, confirmPass: String): String? {
        return validate.validateConfirmPassword(pass, confirmPass)
    }
}