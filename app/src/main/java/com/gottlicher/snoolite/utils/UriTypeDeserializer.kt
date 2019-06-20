package com.gottlicher.snoolite.utils

import android.net.Uri
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.TypeAdapter
import java.lang.reflect.Type

class UriTypeDeserializer : JsonDeserializer<Uri> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Uri {
        val strVal = json?.asString
        return Uri.parse(strVal?.replace("amp;",""))
    }

}