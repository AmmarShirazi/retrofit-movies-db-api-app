package com.example.moviesapp.movieservice

import android.content.Context
import android.util.Log
import com.example.moviesapp.Constant
import com.example.moviesapp.model.MovieData
import com.example.moviesapp.model.MovieResponse
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class FreeMoviesDatabaseRepository @Inject constructor() {


    val gson = GsonBuilder()
        .registerTypeAdapter(MovieResponse::class.java, MovieResponseDeserializer())
        .create()

    var retrofit = Retrofit.Builder()
        .baseUrl("https://moviesdatabase.p.rapidapi.com")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    var service: FreeMoviesDatabaseService = retrofit.create(FreeMoviesDatabaseService::class.java)


    fun getMoviesList(apiKey: String, title: String, pageNumber: String) : Call<MovieResponse> {
        return service.getMovies(apiKey, title, pageNumber)
    }

    fun getMovieDetails(apiKey: String, movieId: String): Call<MovieResponse> {

        return service.getDetails(apiKey, movieId)

    }


}