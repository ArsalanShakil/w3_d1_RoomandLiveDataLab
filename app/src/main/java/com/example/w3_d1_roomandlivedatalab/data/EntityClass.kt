package com.example.w3_d1_roomandlivedatalab.data

import androidx.room.*
import java.util.*

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = false)
    val iid: Long? = null,
    val movie_name: String,
    val release_date: String,
    val director: String,

    ) {
    override fun toString() = "($movie_name) $release_date $director"
}

@Entity
data class Actors(
    @PrimaryKey(autoGenerate = true)
    val mid : Int? = null,
    val movie_name: String,
    val actor_name: String,
    val role : String
)


data class MovieCast(
    @Embedded
    val movie: Movie,

    @Relation(parentColumn = "movie_name", entityColumn = "movie_name")
    val actor_name: List<Actors>
)