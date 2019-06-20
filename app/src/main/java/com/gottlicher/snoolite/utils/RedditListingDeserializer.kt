package com.gottlicher.snoolite.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.gottlicher.snoolite.api.RedditComment
import com.gottlicher.snoolite.api.RedditDataWrapper
import com.gottlicher.snoolite.api.RedditPermalink
import com.gottlicher.snoolite.api.RedditPost
import java.lang.reflect.Type

class RedditListingDeserializer : JsonDeserializer<RedditPermalink> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): RedditPermalink {
        val arr = json?.asJsonArray
        val post = context?.deserialize<RedditDataWrapper<RedditPost>>(arr?.get(0), object : TypeToken<RedditDataWrapper<RedditPost>>() {}.type)
        val comments = context?.deserialize<RedditDataWrapper<RedditComment>>(arr?.get(1), object : TypeToken<RedditDataWrapper<RedditComment>>() {}.type)
        return RedditPermalink (post!!.data.children[0].data, comments!!.data.children.map { it.data })
    }
}