package com.gottlicher.snoolite.home

import android.net.Uri
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gottlicher.snoolite.R
import com.gottlicher.snoolite.api.DataState
import com.gottlicher.snoolite.api.RedditPost
import com.gottlicher.snoolite.databinding.ViewPostBinding

class PostViewHolder (val item: ViewPostBinding) : RecyclerView.ViewHolder(item.root){
    public fun bind (post:RedditPost?) {
        if (post == null) {
            item.post = RedditPost("Error loading",
                123,
                "",
                Uri.EMPTY,
                System.currentTimeMillis(),
                "",
                false,
                "",
                0,
                "",
                "",
                null,
                "",
                "")
        } else {
            item.post = post
        }
    }
}

class LoadingViewHolder (val item: View) : RecyclerView.ViewHolder (item) {

    private val loadingbar:ProgressBar = item.findViewById(R.id.loading_bar)
    private val errorText:TextView = item.findViewById(R.id.error_text)

    fun bind (state: DataState) {
        errorText.visibility = if (state == DataState.ERROR) View.VISIBLE else View.GONE
        loadingbar.visibility = if (state == DataState.LOADING) View.VISIBLE else View.GONE
    }
}