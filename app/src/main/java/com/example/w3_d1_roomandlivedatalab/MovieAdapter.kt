package com.example.w3_d1_roomandlivedatalab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.w3_d1_roomandlivedatalab.data.Movie


class MovieAdapter(var onClick: (String) -> (Unit) = { }, var items : MutableList<Movie> = mutableListOf()) : RecyclerView.Adapter<MovieAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.movieNameTxt.text = items[position].movie_name

        holder.movieNameTxt.setOnClickListener {
            onClick(items[position].movie_name)
        }

    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val movieNameTxt: TextView = itemView.findViewById(R.id.movieNameTxt)
    }
}