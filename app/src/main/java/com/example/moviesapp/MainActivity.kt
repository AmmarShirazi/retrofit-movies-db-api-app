package com.example.moviesapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.adapter.MoveListAdapter
import com.example.moviesapp.model.MovieResponse
import com.example.moviesapp.model.RVMovieModel
import com.example.moviesapp.movieservice.FreeMoviesDatabaseRepository
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var db: FreeMoviesDatabaseRepository
    private var movieResponse: MovieResponse? = null
    private lateinit var moveListAdapter: MoveListAdapter
    private val rvMovieData = ArrayList<RVMovieModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var nextPageButton: Button
    private lateinit var refreshButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        recyclerView = findViewById(R.id.recycler_view)
        nextPageButton = findViewById(R.id.next_page_button)
        refreshButton = findViewById(R.id.refresh_button)

        moveListAdapter = MoveListAdapter(rvMovieData, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = moveListAdapter

        refreshList()

        nextPageButton.setOnClickListener {
            loadNextPage()
        }
        refreshButton.setOnClickListener{
            refreshList()
        }

    }

    private fun loadNextPage() {

        rvMovieData.clear()
        moveListAdapter.notifyDataSetChanged()

        if (movieResponse != null && movieResponse!!.next != null) {

            val moveCall = db.getMoviesList(Constant.FreeMoviesDatabaseAPIKey, "titles", movieResponse!!.next.substringAfter('='))
            moveCall.enqueue(object : Callback<MovieResponse> {
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    if (response.isSuccessful) {
                        movieResponse = response.body()!!
                        populateMoviesListView()

                    } else {
                        Toast.makeText(applicationContext, "Failed to fetch movie list", Toast.LENGTH_SHORT)
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Failed to fetch movie list", Toast.LENGTH_SHORT)
                }
            })
        }



    }

    private fun refreshList() {

        rvMovieData.clear()
        moveListAdapter.notifyDataSetChanged()

        val moveCall = db.getMoviesList(Constant.FreeMoviesDatabaseAPIKey,"titles", "1")

        moveCall.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    movieResponse = response.body()!!
                    populateMoviesListView()

                } else {
                    Toast.makeText(applicationContext, "Failed to fetch movie list", Toast.LENGTH_SHORT)
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to fetch movie list", Toast.LENGTH_SHORT)
            }
        })
    }

    private fun populateMoviesListView() {

        for (movie in movieResponse?.results!!) {
            val movieModel = RVMovieModel(movieId = movie.id ,name = movie.titleText.text, imgRef = movie.primaryImage?.url.toString(), year = movie.releaseYear.year.toString())
            rvMovieData.add(movieModel)
            moveListAdapter.notifyItemInserted(rvMovieData.size)
        }

    }
}