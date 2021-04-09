package com.shubham.ishare.validation

class IdeaValidate {

    fun validateTitle(title: String): String?{

        if(title == ""){
            return "Title is required"
        }

        if(title.length < 4){
            return "Title should contain at least 4 characters"
        }

        return null
    }

    fun validateDescription(description: String): String?{

        if(description == ""){
            return "Description is required"
        }

        if(description.length < 20){
            return "Description should contain at least 20 characters"
        }

        return null
    }
}