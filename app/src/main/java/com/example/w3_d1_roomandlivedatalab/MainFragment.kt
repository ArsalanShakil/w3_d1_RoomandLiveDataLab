package com.example.w3_d1_roomandlivedatalab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.w3_d1_roomandlivedatalab.data.Movie
import com.example.w3_d1_roomandlivedatalab.data.MovieDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        adapter = MovieAdapter()
        adapter.onClick = { showDetail(it) }

        recyclerView.adapter = adapter
        refreshAsync()

        view.findViewById<Button>(R.id.addMovieBtn).setOnClickListener {
            add()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(requireContext().applicationContext)

    }

    private fun showDetail(movie : String)
    {
        GlobalScope.launch {
            val db = MovieDB.get(requireContext().applicationContext)
            val movie = db.moviecastDao().getMovie(movie)


            withContext(Dispatchers.Main) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, DetailsFragment.newInstance(moviecast = movie))
                    .addToBackStack("my_fragment")
                    .commit()
            }
        }
    }

    private fun refreshAsync(movie: List<Movie>? = null)
    {
        if(movie == null) {
            GlobalScope.launch {
                val movie = MovieDB.get(requireContext().applicationContext).movieDao().getAll()

                withContext(Dispatchers.Main) {
                    adapter.items = movie.toMutableList()
                    adapter.notifyDataSetChanged()
                }
            }
        }
        else
        {
            adapter.items = movie.toMutableList()
            adapter.notifyDataSetChanged()
        }
    }

    private fun add() {
        GlobalScope.launch {
            val db = MovieDB.get(requireContext().applicationContext)
                db.movieDao().insert(
                    Movie(
                        movie_name = "Add movie here",
                        release_date = "",
                        director = "",

                    )
                )


                refreshAsync()
        }
    }
}