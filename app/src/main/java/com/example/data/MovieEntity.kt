package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist_movies")
data class MovieEntity(
    @PrimaryKey val id: String,
    val title: String,
    val posterUrl: String,
    val backdropUrl: String,
    val description: String,
    val genre: String,
    val rating: String,
    val releaseYear: String,
    val duration: String,
    val cast: String,
    val addedAt: Long = System.currentTimeMillis()
)
