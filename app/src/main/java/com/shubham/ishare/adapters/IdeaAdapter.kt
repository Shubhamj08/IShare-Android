package com.shubham.ishare.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shubham.ishare.R

class IdeaAdapter: RecyclerView.Adapter<IdeaAdapter.IdeaViewHolder>() {
    var data = listOf<Int>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: IdeaViewHolder, position: Int) {
        val idea = data[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdeaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.idea, parent, false)

        return IdeaViewHolder(view)
    }

    class IdeaViewHolder(private val idea: View): RecyclerView.ViewHolder(idea){
        val title: TextView = idea.findViewById(R.id.title)
        val description: TextView = idea.findViewById(R.id.description)
        val likes: Button = idea.findViewById(R.id.liked)
        val share: Button = idea.findViewById(R.id.share)
    }
}

