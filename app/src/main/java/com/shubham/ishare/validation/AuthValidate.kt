package com.shubham.ishare.validation

class AuthValidate {
    fun validateUsername(username: String): String? {
        if(username == "")
            return "Username is required"

        if(username.length < 4)
            return "Username should have at least 4 characters"

        return null
    }

    fun validateEmail(email: String): String? {
        if(email == "")
            return "Email is required"

        val pattern = Regex("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\$")
        if(!pattern.matches(email))
            return "Enter a valid email"

        return null
    }

    fun validatePassword(password: String): String? {
        if(password == "")
            return "Password is required"

        if(password.length < 8)
            return "Password should have at least 8 characters"

        return null
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): String? {
        if(confirmPassword == "")
            return "Confirm Password is required"

        if(confirmPassword != password)
            return "Passwords do not match"

        return null
    }
}