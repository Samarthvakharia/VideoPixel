package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM watchlist_movies ORDER BY addedAt DESC")
    fun getWatchlist(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Query("DELETE FROM watchlist_movies WHERE id = :id")
    suspend fun deleteMovieById(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM watchlist_movies WHERE id = :id)")
    fun observeIsAdded(id: String): Flow<Boolean>
}
