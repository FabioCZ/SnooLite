package com.gottlicher.snoolite.home

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.gottlicher.snoolite.api.EMPTY_POST
import com.gottlicher.snoolite.api.RedditPost
import com.gottlicher.snoolite.api.RedditPostType
import com.gottlicher.snoolite.databinding.ViewPostBinding
import org.jetbrains.anko.sdk27.coroutines.onClick

class PostViewHolder (val item: ViewPostBinding) : RecyclerView.ViewHolder(item.root){
    public fun bind (post:RedditPost?, onClick:(RedditPost) -> Unit) {
        if (post == null) {
            item.post = EMPTY_POST
        } else {
            item.post = post
            item.root.onClick { onClick (post) }
        }
    }
}