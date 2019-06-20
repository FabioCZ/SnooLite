package com.gottlicher.snoolite.api

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class RedditDataWrapper<T> (val data:RedditObjectData<T>)

data class RedditObjectData<T> (val children:List<RedditChildWrapper<T>>)

data class RedditChildWrapper<T> (val kind: String, val data:T)

enum class  RedditPostType {
    @SerializedName("image")IMAGE,
    @SerializedName("link")LINK,
    @SerializedName("self")SELF
}

data class RedditPost (
    var title:String,
    var score:Long,
    var author:String,
    var url:Uri,
    var createdUtc:Long,
    var permalink:String,
    @SerializedName("over_18")var over18:Boolean,
    var domain:String?,
    var gilded:Long,
    var selftext:String?,
    var thumbnail:String,
    var preview:Preview?,
    var subreddit:String,
    var name:String,
    var postHint:RedditPostType
)


data class RedditPermalink (var post:RedditPost,
                            var comments:List<RedditComment>)

//no handling for replies, only top level comments :(
//Also should probably have a common base object for comments + posts
data class RedditComment(var body:String,
                         var author:String,
                         var score:Long,
                         var createdUtc:Long,
                         var name:String)

val EMPTY_POST: RedditPost
    get() = RedditPost("",
        0,
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
        "",
        RedditPostType.SELF)

class RedditPostsDiffer : DiffUtil.ItemCallback<RedditPost>() {
    override fun areItemsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean = oldItem.name == newItem.name
    override fun areContentsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean = oldItem.name == newItem.name
}

class RedditCommentsDiffer : DiffUtil.ItemCallback<RedditComment>() {
    override fun areItemsTheSame(oldItem: RedditComment, newItem: RedditComment): Boolean = oldItem.name == newItem.name
    override fun areContentsTheSame(oldItem: RedditComment, newItem: RedditComment): Boolean = oldItem.name == newItem.name
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