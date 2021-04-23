package com.shubham.ishare.ideas

import com.shubham.ishare.user
import com.squareup.moshi.Json
import java.util.*

data class Idea(
    var _id: String,
    var tags: List<String>,
    var likes: List<String>,
    var title: String,
    var description: String,
    @Json(name = "user")
    var author: String,
    val date: String,
    var liked: Boolean = likes.contains(user),
    var nLikes: Int = likes.size
)