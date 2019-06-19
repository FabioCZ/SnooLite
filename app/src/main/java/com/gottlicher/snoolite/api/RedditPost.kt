package com.gottlicher.snoolite.api

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

data class RedditPostsResponse (val data:RedditPostData)

data class RedditPostData (val children:List<RedditPostWrapper>)

data class RedditPostWrapper (val kind: String, val data:RedditPost)

data class RedditPost (
    var title:String,
    var score:Long,
    var author:String,
    var url:Uri,
    var createdUtc:Long,
    var permalink:String,
    var over18:Boolean,
    var domain:String,
    var gilded:Long,
    var selfText:String,
    var thumbnail:String,
    var preview:Preview?,
    var subreddit:String
)

class RedditPostsDiffer : DiffUtil.ItemCallback<RedditPost>() {
    override fun areItemsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean = oldItem.url == newItem.url
    override fun areContentsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean = oldItem.url == newItem.url
}

data class Preview (
    var images: List<ImagePreview>
)

data class ImagePreview (
    var source:ImageData,
    var resolutions: List<ImageData>
)

data class ImageData (
    var url:Uri,
    var width:Int,
    var height:Int
)