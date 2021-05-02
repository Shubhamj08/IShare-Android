package com.shubham.ishare.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.shubham.ishare.R
import com.shubham.ishare.databinding.IdeaBinding
import com.shubham.ishare.ideas.Idea
import com.shubham.ishare.jwt
import com.shubham.ishare.services.Backend
import com.shubham.ishare.services.LikeService
import com.shubham.ishare.user
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

val likeResponse = MutableLiveData<Idea>()
class IdeaAdapter(val context: Context): ListAdapter<Idea, IdeaAdapter.IdeaViewHolder>(IdeaDiffCallback()) {

    private val likeService = LikeService()
    override fun onBindViewHolder(holder: IdeaViewHolder, position: Int) {
        val idea = getItem(position)
        holder.bind(idea)

        holder.liked.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                if (idea!!.liked || idea.likes.contains(user.value?._id)) {
                    holder.liked.apply {
                        setIconResource(R.drawable.like_o)
                        text = (idea.nLikes - 1).toString()
                        idea.liked = false
                        idea.nLikes -= 1
                    }
                    try {
                        likeResponse.value = Backend.retrofitService.unlike(jwt!!, idea)
                        Log.i("like", likeResponse.value.toString())
                        likeService.onLikeClick()
                    } catch (ex: Exception) {
                        Log.i("like", "Failed: ${ex.message}")
                    }

                } else {
                    holder.liked.apply {
                        setIconResource(R.drawable.like)
                        text = (idea.nLikes + 1).toString()
                        idea.liked = true
                        idea.nLikes += 1
                    }
                    try {
                        likeResponse.value = Backend.retrofitService.like(jwt!!, idea)
                        Log.i("like", likeResponse.value.toString())
                        likeService.onLikeClick()
                    } catch (ex: Exception) {
                        Log.i("like", "Failed: ${ex.message}")
                    }
                }
            }
        }

        holder.share.setOnClickListener {
            context.startActivity(getShareIntent(idea))
        }
    }

    private fun getShareIntent(idea: Idea): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent
            .setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, "${idea.title}\n${idea.description}")
        return shareIntent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdeaViewHolder {
        return IdeaViewHolder.from(parent)
    }

    class IdeaViewHolder(private val binding: IdeaBinding): RecyclerView.ViewHolder(binding.root){
        val share: ImageButton = binding.share
        val liked = binding.liked
        fun bind(idea: Idea?){
            binding.idea = idea
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): IdeaViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = IdeaBinding.inflate(layoutInflater, parent, false)
                return IdeaViewHolder(binding)
            }
        }
    }
}

class IdeaDiffCallback: DiffUtil.ItemCallback<Idea>(){
    override fun areItemsTheSame(oldItem: Idea, newItem: Idea): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: Idea, newItem: Idea): Boolean {
        return oldItem == newItem
    }

}

