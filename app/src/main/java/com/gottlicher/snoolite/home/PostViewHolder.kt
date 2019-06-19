package com.gottlicher.snoolite.home

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gottlicher.snoolite.api.RedditPost
import com.gottlicher.snoolite.databinding.ViewPostBinding

class PostModel (

)
class PostViewHolder (val item: ViewPostBinding) : RecyclerView.ViewHolder(item.root){
    public fun bind (post:RedditPost?) {
        if (post == null) {
            item.post = RedditPost("Error loading", 123, "", Uri.EMPTY, System.currentTimeMillis(), "", false, "",0,"","",null, "")
        } else {
            item.post = post
        }
    }
}