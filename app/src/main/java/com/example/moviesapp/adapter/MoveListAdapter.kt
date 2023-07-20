package com.example.moviesapp.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.moviesapp.Constant
import com.example.moviesapp.MainActivity
import com.example.moviesapp.MovieDetailsActivity
import com.example.moviesapp.R
import com.example.moviesapp.model.RVMovieModel

class MoveListAdapter(private val dataSet: ArrayList<RVMovieModel>, private val context: Activity) : RecyclerView.Adapter<MoveListAdapter.ViewHolder>()  {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieName: TextView
        val movieImage: ImageView
        val movieYear: TextView
        val rowHolder: ConstraintLayout

        init {
            movieName = view.findViewById(R.id.movie_name_field)
            movieImage = view.findViewById(R.id.movie_image)
            movieYear = view.findViewById(R.id.movie_year_field)
            rowHolder = view.findViewById(R.id.row_wrapper)


        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.movie_row_primary, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.movieName.text = dataSet[position].name
        viewHolder.movieYear.text = dataSet[position].year

        Glide.with(context)
            .load(dataSet[position].imgRef).
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
            .apply(RequestOptions().override(400,400).placeholder(R.drawable.baseline_downloading_24).centerInside().fitCenter().circleCrop())
            .into(viewHolder.movieImage)


        viewHolder.rowHolder.setOnClickListener {
            var intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra("movieId", dataSet[position].movieId)
            context.startActivity(intent)
        }


    }

    override fun getItemCount() = dataSet.size

}