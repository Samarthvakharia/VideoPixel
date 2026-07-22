package com.example.data

import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {
    val watchlist: Flow<List<MovieEntity>> = movieDao.getWatchlist()

    suspend fun addToWatchlist(movie: MovieEntity) {
        movieDao.insertMovie(movie)
    }

    suspend fun removeFromWatchlist(id: String) {
        movieDao.deleteMovieById(id)
    }

    fun isAdded(id: String): Flow<Boolean> {
        return movieDao.observeIsAdded(id)
    }
}
