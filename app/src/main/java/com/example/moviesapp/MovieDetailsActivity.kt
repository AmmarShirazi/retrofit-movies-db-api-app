package com.example.moviesapp

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.moviesapp.model.MovieData
import com.example.moviesapp.model.MovieResponse
import com.example.moviesapp.movieservice.FreeMoviesDatabaseRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var db: FreeMoviesDatabaseRepository
    private var movieDetails: MovieData? = null

    private lateinit var backButton: Button
    private lateinit var movieImage: ImageView
    private lateinit var titleText: TextView
    private lateinit var yearText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.movie_details_activity)
        if (!intent.hasExtra("movieId")) {
            startActivity(Intent(this@MovieDetailsActivity, MainActivity::class.java))
        }

        backButton = findViewById(R.id.back_button)
        movieImage = findViewById(R.id.movie_image)
        titleText = findViewById(R.id.title_text)
        yearText = findViewById(R.id.release_text)

        backButton.setOnClickListener {
            startActivity(Intent(this@MovieDetailsActivity, MainActivity::class.java))
        }

        val movieId = intent.getStringExtra("movieId")
        loadMovieData(movieId)



    }

    private fun loadMovieData(movieId: String?) {

        Log.d("Movie ID", movieId!!)
        val movieCall = db.getMovieDetails(Constant.FreeMoviesDatabaseAPIKey, movieId!!)



        movieCall.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    movieDetails = response.body()?.results?.get(0)
                    populateViews()

                } else {
                    Log.d("MovieDetailsDEBUG", "here1")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MovieDetailsDEBUG", "here2")
            }
        })

    }

    private fun populateViews() {
        titleText.text = movieDetails!!.titleText.text
        yearText.text = movieDetails!!.releaseYear.year.toString()

        Glide.with(applicationContext)
            .load(movieDetails?.primaryImage?.url).
            listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                    e?.printStackTrace()
                    Log.d("GLIDE DEBUG", "Image not loaded")
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    Log.d("GLIDE DEBUG", "Image loaded")
                    return false
                }
            })
            .apply(RequestOptions().override(1200,1200).placeholder(R.drawable.baseline_downloading_24).centerInside().fitCenter().circleCrop())
            .into(movieImage)
    }

}