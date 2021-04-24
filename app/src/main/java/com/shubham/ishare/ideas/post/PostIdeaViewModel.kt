package com.shubham.ishare.ideas.post

import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shubham.ishare.validation.IdeaValidate

class PostIdeaViewModel: ViewModel() {

    private val _title = MutableLiveData<String>()
    val title: MutableLiveData<String>
    get() = _title

    private val _description = MutableLiveData<String>()
    val description: MutableLiveData<String>
        get() = _description

    var titleError: String? = null
    var descriptionError: String? = null

    fun onSubmit(t: EditText, d: EditText): Boolean{
        titleError = validateTitle(t.text.toString())
        descriptionError = validateDescription(d.text.toString())
        _title.value = t.text.toString()
        _description.value = d.text.toString()

        if(titleError == null && descriptionError == null)
            return true

        return false
    }

    private val validate = IdeaValidate()

    fun validateTitle(title: String): String?{
        return validate.validateTitle(title)
    }

    fun validateDescription(desc: String): String?{
        return validate.validateDescription(desc)
    }
}