package com.gottlicher.snoolite.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gottlicher.snoolite.R
import com.gottlicher.snoolite.api.DataState
import com.gottlicher.snoolite.api.RedditPost
import com.gottlicher.snoolite.api.RedditPostsDiffer
import com.gottlicher.snoolite.databinding.ViewPostBinding



class PostsAdapater : PagedListAdapter<RedditPost, PostViewHolder> (RedditPostsDiffer()) {

    init {
//        setHasStableIds(true)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ViewPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}