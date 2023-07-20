package com.example.moviesapp.movieservice

import com.example.moviesapp.model.MovieData
import com.example.moviesapp.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface FreeMoviesDatabaseService {
    @Headers("X-RapidAPI-Host: moviesdatabase.p.rapidapi.com")
    @GET("{path}")
    fun getMovies(@Header("X-RapidAPI-Key") apiKey: String, @Path("path") path: String, @Query("page") pageNumber: String): Call<MovieResponse>

    @Headers("X-RapidAPI-Host: moviesdatabase.p.rapidapi.com")
    @GET("titles/{movieId}")
    fun getDetails(@Header("X-RapidAPI-Key") apiKey: String, @Path("movieId") movieId: String): Call<MovieResponse>

}
