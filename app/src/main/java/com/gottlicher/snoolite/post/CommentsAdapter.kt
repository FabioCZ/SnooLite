package com.gottlicher.snoolite.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.gottlicher.snoolite.api.RedditComment
import com.gottlicher.snoolite.api.RedditCommentsDiffer
import com.gottlicher.snoolite.databinding.ViewCommentBinding

class CommentsAdapter  : ListAdapter<RedditComment, CommentsViewHolder> (RedditCommentsDiffer()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val binding = ViewCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}