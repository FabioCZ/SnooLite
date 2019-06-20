package com.gottlicher.snoolite.post

import androidx.recyclerview.widget.RecyclerView
import com.gottlicher.snoolite.api.RedditComment
import com.gottlicher.snoolite.databinding.ViewCommentBinding

class CommentsViewHolder (val item: ViewCommentBinding) : RecyclerView.ViewHolder(item.root){
    public fun bind (comment:RedditComment) {
        item.comment = comment
    }
}