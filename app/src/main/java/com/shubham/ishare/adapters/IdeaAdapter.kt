package com.shubham.ishare.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.shubham.ishare.R
import com.shubham.ishare.ideas.Idea
import com.shubham.ishare.user

class IdeaAdapter(): RecyclerView.Adapter<IdeaAdapter.IdeaViewHolder>() {

    var data: List<Idea>? = listOf<Idea>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount() = data!!.size

    override fun onBindViewHolder(holder: IdeaViewHolder, position: Int) {
        val idea = data?.get(position)
        holder.apply {
            title.text = idea?.title
            description.text = idea?.description
            like.text = idea?.nLikes.toString()
            if(idea!!.liked || idea.likes.contains(user.value?._id)) {
                like.setIconResource(R.drawable.like)
            }
            else
                like.setIconResource(R.drawable.like_o)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdeaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.idea, parent, false)

        return IdeaViewHolder(view)
    }

    class IdeaViewHolder(private val idea: View): RecyclerView.ViewHolder(idea){
        val title: TextView = idea.findViewById(R.id.title)
        val description: TextView = idea.findViewById(R.id.description)
        val like: MaterialButton = idea.findViewById(R.id.liked)
        val share: MaterialButton = idea.findViewById(R.id.share)
    }
}

