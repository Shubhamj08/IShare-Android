package com.shubham.ishare.ideas

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class IdeasViewModel: ViewModel() {

    val ideas = listOf<Int>(1, 2, 3, 4, 5)

}