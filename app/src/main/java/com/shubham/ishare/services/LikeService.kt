package com.shubham.ishare.services

import android.util.Log
import com.shubham.ishare.adapters.likeResponse
import com.shubham.ishare.ideas.Idea
import com.shubham.ishare.ideasResponse

class LikeService {
    fun onLikeClick(){
        for(it: Idea in ideasResponse.value!!){
            if(it._id == likeResponse.value?._id){
                it.likes = likeResponse.value!!.likes
                it.liked = likeResponse.value!!.liked
                it.nLikes = likeResponse.value!!.nLikes
                break
            }
        }
    }
}