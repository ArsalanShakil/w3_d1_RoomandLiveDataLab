package com.example.w3_d1_roomandlivedatalab

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.example.w3_d1_roomandlivedatalab.data.Actors
import com.example.w3_d1_roomandlivedatalab.data.Movie
import com.example.w3_d1_roomandlivedatalab.data.MovieCast
import com.example.w3_d1_roomandlivedatalab.data.MovieDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsFragment(private val moviecast: MovieCast) : Fragment() {

    companion object {
        fun newInstance(moviecast: MovieCast) = DetailsFragment(moviecast)
    }

    lateinit var layout: ConstraintLayout
    lateinit var movielist: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        showBackButton()
        layout = inflater.inflate(R.layout.fragment_details, container, false) as ConstraintLayout
        movielist = layout.findViewById<LinearLayout>(R.id.linearLayoutMovieList)!!

        refreshActor()

        // Title
        layout.findViewById<EditText>(R.id.editTextName).setText(moviecast.movie.movie_name)

        // Description
        layout.findViewById<EditText>(R.id.editTextYear)
            .setText(moviecast.movie.release_date)

        layout.findViewById<EditText>(R.id.editTextDirector)
            .setText(moviecast.movie.director)

        layout.findViewById<Button>(R.id.addActorBtn).setOnClickListener {
            addActor()
        }

        // Delete
        layout.findViewById<ImageView>(R.id.btnDelete).setOnClickListener {
            remove()
        }

        return layout
    }

    private fun showBackButton(show: Boolean = true) {
        if (activity is AppCompatActivity) {
            setHasOptionsMenu(show)
            (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(show)
        }
    }

    private fun refreshActor() {

        moviecast.actor_name.forEach {
            var view = layoutInflater.inflate(R.layout.actors_view, null)
            val actor = view.findViewById<EditText>(R.id.editTextActor)
            actor.setText(it.actor_name)
            val role = view.findViewById<TextView>(R.id.editTextRole)
            role.setText(it.role)
            val remove = view.findViewById<ImageView>(R.id.btnRemove)
            remove.setOnClickListener {
                movielist.removeView(view)
            }
            movielist.addView(view)
        }
    }

    private fun remove()
    {
        GlobalScope.launch {
            val db = MovieDB.get(requireContext().applicationContext)
            db.actorsDao().deleteActors(moviecast.movie.movie_name)
            db.movieDao().deleteMovie(movie_name = moviecast.movie.movie_name)

            withContext(Dispatchers.Main) {
                showBackButton(false)
                requireActivity().onBackPressed()
            }
        }
    }

    private fun addActor() {
        var view = layoutInflater.inflate(R.layout.actors_view, null)
        val remove = view.findViewById<ImageView>(R.id.btnRemove)
        remove.setOnClickListener {
            movielist.removeView(view)
        }
        movielist.addView(view)
    }

    private fun commitChangesAndPop() {
        GlobalScope.launch {
            val db = MovieDB.get(requireContext().applicationContext)
            db.actorsDao().deleteActors(moviecast.movie.movie_name)

            db.movieDao().update(
                Movie(
                    moviecast.movie.iid,
                    layout.findViewById<EditText>(R.id.editTextName).text.toString(),
                    layout.findViewById<EditText>(R.id.editTextYear).text.toString(),
                    layout.findViewById<EditText>(R.id.editTextDirector).text.toString()

                )
            )


            movielist.children.forEach {
                db.actorsDao().insert(
                    Actors(
                        movie_name = layout.findViewById<EditText>(R.id.editTextName).text.toString(),
                        actor_name = it.findViewById<EditText>(R.id.editTextActor).text.toString(),
                        role = it.findViewById<TextView>(R.id.editTextRole).text.toString()
                    )
                )
            }

            withContext(Dispatchers.Main) {
                showBackButton(false)
                requireActivity().onBackPressed()
            }
        }
    }

    // Actionbar back press
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        commitChangesAndPop()
        return true
    }

    // Device back press
    override fun onResume() {
        super.onResume()
        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()
        requireView().setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                commitChangesAndPop()
                true
            } else false
        }
    }

}