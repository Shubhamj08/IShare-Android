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

    fun onSubmit(t: EditText, d: EditText){
        _title.value = t.text.toString()
        _description.value = d.text.toString()
    }

    private val validate = IdeaValidate()

    fun validateTitle(): String?{
        return validate.validateTitle(_title.value.toString())
    }

    fun validateDescription(): String?{
        return validate.validateDescription(_description.value.toString())
    }
}