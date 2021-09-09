package com.example.w3_d1_roomandlivedatalab

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.w3_d1_roomandlivedatalab.data.Movie

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class MovieAdapter(var onClick: (String) -> (Unit) = { }, var items : MutableList<Movie> = mutableListOf()) : RecyclerView.Adapter<MovieViewHolder>()
{

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder = MovieViewHolder(TextView(parent.context))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int
    ) {
        Log.e("DBG","Called onBindViewHolder")
        (holder.itemView as TextView).text = items[position].movie_name
        holder.itemView.setOnClickListener {
            onClick(items[position].movie_name)
        }
    }
}