package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.data.MovieDatabase
import com.example.data.MovieRepository
import com.example.ui.NetflixApp
import com.example.ui.NetflixViewModel
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = MovieDatabase.getDatabase(applicationContext)
        val repository = MovieRepository(database.movieDao())
        
        val viewModel: NetflixViewModel by viewModels {
            NetflixViewModel.provideFactory(repository)
        }

        setContent {
            MyApplicationTheme {
                NetflixApp(viewModel = viewModel)
            }
        }
    }
}
