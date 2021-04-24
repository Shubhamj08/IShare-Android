package com.shubham.ishare.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.shubham.ishare.R
import com.shubham.ishare.ideas.Idea
import com.shubham.ishare.services.Backend
import com.shubham.ishare.user
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope

@BindingAdapter("setTitle")
    fun TextView.setTitle(idea: Idea){
        text = idea.title
    }

    @BindingAdapter("setDescription")
    fun TextView.setDescription(idea: Idea){
        text = idea.description
    }

    @BindingAdapter("setLike")
    fun MaterialButton.setLike(idea: Idea){
        if(idea.liked || idea.likes.contains(user.value?._id))
            setIconResource(R.drawable.like)
        else
            setIconResource(R.drawable.like_o)

        text = idea.nLikes.toString()
    }
