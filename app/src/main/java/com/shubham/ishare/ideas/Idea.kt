package com.shubham.ishare.ideas

import java.util.*

data class Idea(
    var _id: String,
    var tags: List<String>,
    var likes: List<String>,
    var title: String,
    var description: String,
    var user: String,
    val date: String,
    var liked: Boolean = false,
    var nLikes: Int = likes.size
)