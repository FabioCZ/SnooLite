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



class PostsAdapater : PagedListAdapter<RedditPost, RecyclerView.ViewHolder> (RedditPostsDiffer()) {

    val TYPE_POST:Int = 1
    val TYPE_LOADER:Int = 2

    var state:DataState = DataState.NONE

    init {
//        setHasStableIds(true)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_POST) {
            val binding = ViewPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PostViewHolder(binding)
        } else {
            return LoadingViewHolder(LayoutInflater.from(parent.context).inflate(com.gottlicher.snoolite.R.layout.view_loading_error, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PostViewHolder) {
            holder.bind(getItem(position))
        } else if (holder is LoadingViewHolder) {
            holder.bind(state)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (state != DataState.LOADED && position == itemCount - 1) {
            TYPE_LOADER
        } else {
            TYPE_POST
        }
    }

    private fun hasExtraRow(): Boolean {
        return state !== DataState.LOADED
    }

    fun setDataState(newNetworkState: DataState) {
        val previousState = this.state
        val previousExtraRow = hasExtraRow()
        this.state = newNetworkState
        val newExtraRow = hasExtraRow()
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRow && previousState !== newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }
}