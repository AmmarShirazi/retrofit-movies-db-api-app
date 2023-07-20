package com.example.moviesapp.movieservice

import com.example.moviesapp.model.MovieData
import com.example.moviesapp.model.MovieResponse
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class MovieResponseDeserializer: JsonDeserializer<MovieResponse> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): MovieResponse {
        val jsonObject = json?.asJsonObject

        val page = jsonObject?.get("page")?.asInt
        val next = jsonObject?.get("next")?.asString
        val entries = jsonObject?.get("entries")?.asInt
        val resultsElement = jsonObject?.get("results")

        var results: List<MovieData> = emptyList()
        resultsElement?.let {
            results = if (it.isJsonArray) {
                it.asJsonArray.map { el -> context?.deserialize<MovieData>(el, MovieData::class.java) }.filterNotNull()
            } else {
                listOf(context?.deserialize<MovieData>(it, MovieData::class.java)).filterNotNull()
            }
        }

        return MovieResponse(page ?: 0, next ?: "", entries ?: 0, results)
    }
}
