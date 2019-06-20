package com.gottlicher.snoolite.utils

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.gottlicher.snoolite.R
import com.gottlicher.snoolite.api.RedditPost
import com.gottlicher.snoolite.api.RedditPostType
import org.jetbrains.anko.imageResource
import ru.noties.markwon.Markwon
import java.lang.Exception
import java.text.DecimalFormat


@BindingAdapter("score")
public fun setScore(view: TextView, score:Long){
    var scoreString = score.toString()
    if (score >= 1000) {
        val scoreThousands = score / 1000.0
        scoreString = DecimalFormat("#.#").format(scoreThousands) + "k"
    }
    view.text = scoreString
}

@SuppressLint("SetTextI18n")
@BindingAdapter("author")
public  fun setAuthor (view: TextView, author: String) {
    view.text = "u/$author"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("subreddit")
public  fun setSubreddit (view: TextView, sub: String) {
    view.text = "r/$sub"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("daysAgo")
public fun setDaysAgo (view: TextView, timeUtc: Long) {
    val now = System.currentTimeMillis() / 1000
    val ageMinutes = (now - timeUtc) / 60
    if (ageMinutes < 0) {
        Log.d("Binding Adapter", "Well, it's a post from the future")
        view.text = "∞"
        return
    }

    if (ageMinutes < 60) {
        view.text = "${ageMinutes}min"
        return
    }

    val ageHours = ageMinutes / 60
    if (ageHours < 24) {
        view.text = "${ageHours}h"
        return
    }

    val ageDays = ageHours / 24
        view.text = "${ageDays}d"
}

@BindingAdapter("previewSrc")
public fun setPreviewSrc (view: ImageView, post:RedditPost ) {
    if (post.postHint == RedditPostType.SELF) {
        view.imageResource = R.drawable.ic_text_fields_grey_24dp
    } else {
        try {
            val previewUri = post.preview!!.images[0].resolutions.lastOrNull { x -> x.width < 300 }?.url
            if (previewUri != null) {
                Glide.with(view).load(previewUri).into(view)
            }
        } catch (e:Exception) {
            Log.d("BINDING", "Failed to retrieve post preview")
            //no op
        }
    }
}

@BindingAdapter("srcUrl")
public fun setSrcUrl (view: ImageView, post: RedditPost ) {
    if (post.postHint != RedditPostType.IMAGE) {
        view.visibility = View.INVISIBLE
    } else if (post.preview != null) {
        view.visibility = View.VISIBLE
        Glide.with(view).load(post.preview!!.images[0].source.url).into(view)
    }
}

@BindingAdapter("postText")
public fun setSelfText(view:TextView, post: RedditPost) {
    if (post.postHint == RedditPostType.IMAGE) {
        view.visibility = View.INVISIBLE
        return
    }
    view.visibility = View.VISIBLE

    val mkdown = Markwon.create(view.context)
    if (post.postHint == RedditPostType.SELF) {
        mkdown.setMarkdown(view, post.selftext ?: "")
    } else {
        val linkMkdown = "[${post.url.toString()}](${post.url.toString()})"
        mkdown.setMarkdown(view, linkMkdown)

    }
}

@BindingAdapter("markWonText")
public fun setMarkWonText(view:TextView, text:String){
    val mkdown = Markwon.create(view.context)
    mkdown.setMarkdown(view, text)
}