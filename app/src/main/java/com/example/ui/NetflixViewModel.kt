package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.api.GeminiService
import com.example.data.Movie
import com.example.data.MovieCatalog
import com.example.data.MovieEntity
import com.example.data.MovieRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

data class ChatMessage(
    val message: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

class NetflixViewModel(private val repository: MovieRepository) : ViewModel() {

    // --- Watchlist State (Room DB Integration) ---
    val watchlist: StateFlow<List<MovieEntity>> = repository.watchlist
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val watchlistIds: StateFlow<Set<String>> = repository.watchlist
        .map { list -> list.map { it.id }.toSet() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

    // --- General Movie Search & Filter States ---
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    // --- Interactive Detail & Simulated Player States ---
    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie = _selectedMovie.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _playerProgress = MutableStateFlow(0f)
    val playerProgress = _playerProgress.asStateFlow()

    private val _isMuted = MutableStateFlow(false)
    val isMuted = _isMuted.asStateFlow()

    // --- AI CineMatch Assistant Chat States (Gemini API) ---
    private val _aiChatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                message = "Hi! I am CineMatch, your personal AI movie assistant. Ask me anything to find the perfect show to watch tonight!",
                isUser = false
            )
        )
    )
    val aiChatMessages = _aiChatMessages.asStateFlow()

    private val _aiChatLoading = MutableStateFlow(false)
    val aiChatLoading = _aiChatLoading.asStateFlow()

    private var playerSimulationJob: Job? = null

    init {
        startProgressSimulation()
    }

    // --- Watchlist Operations ---
    fun toggleWatchlist(movie: Movie) {
        viewModelScope.launch {
            val ids = watchlistIds.value
            if (ids.contains(movie.id)) {
                repository.removeFromWatchlist(movie.id)
            } else {
                repository.addToWatchlist(movie.toEntity())
            }
        }
    }

    fun isAdded(movieId: String): Boolean {
        return watchlistIds.value.contains(movieId)
    }

    // --- Search ---
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        if (query.isEmpty()) {
            _searchResults.value = emptyList()
        } else {
            _searchResults.value = MovieCatalog.searchMovies(query)
        }
    }

    // --- Details & Simulated Player Controls ---
    fun selectMovie(movie: Movie?) {
        _selectedMovie.value = movie
        if (movie == null) {
            stopPlayback()
        }
    }

    fun playMovie(movie: Movie) {
        _selectedMovie.value = movie
        _isPlaying.value = true
        _playerProgress.value = 0f
    }

    fun togglePlayPause() {
        _isPlaying.value = !_isPlaying.value
    }

    fun toggleMute() {
        _isMuted.value = !_isMuted.value
    }

    fun skipForward() {
        val current = _playerProgress.value
        _playerProgress.value = (current + 0.1f).coerceAtMost(1f)
    }

    fun skipBackward() {
        val current = _playerProgress.value
        _playerProgress.value = (current - 0.1f).coerceAtLeast(0f)
    }

    fun setProgress(progress: Float) {
        _playerProgress.value = progress.coerceIn(0f, 1f)
    }

    fun stopPlayback() {
        _isPlaying.value = false
        _playerProgress.value = 0f
    }

    private fun startProgressSimulation() {
        playerSimulationJob?.cancel()
        playerSimulationJob = viewModelScope.launch {
            while (isActive) {
                if (_isPlaying.value) {
                    val current = _playerProgress.value
                    if (current >= 1f) {
                        _playerProgress.value = 0f
                        _isPlaying.value = false // Stop when movie completes
                    } else {
                        _playerProgress.value = current + 0.015f // Increment slowly
                    }
                }
                delay(1000)
            }
        }
    }

    // --- AI Chat Assistant (Gemini API Integration) ---
    fun sendAiChatQuery(query: String) {
        if (query.trim().isEmpty() || _aiChatLoading.value) return

        // Add user message to stack
        val userMsg = ChatMessage(message = query, isUser = true)
        _aiChatMessages.value = _aiChatMessages.value + userMsg
        _aiChatLoading.value = true

        viewModelScope.launch {
            val response = GeminiService.generateRecommendations(query)
            val finalMsg = if (response == "API_KEY_MISSING") {
                "Your Gemini API Key is missing. Please configure it in the AI Studio Secrets panel as GEMINI_API_KEY to enable smart recommendations!"
            } else {
                response
            }
            _aiChatMessages.value = _aiChatMessages.value + ChatMessage(message = finalMsg, isUser = false)
            _aiChatLoading.value = false
        }
    }

    fun clearChat() {
        _aiChatMessages.value = listOf(
            ChatMessage(
                message = "Chat history cleared. What kind of movie, show, or anime would you like to explore now?",
                isUser = false
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        playerSimulationJob?.cancel()
    }

    // --- ViewModel Factory ---
    companion object {
        fun provideFactory(repository: MovieRepository): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NetflixViewModel(repository) as T
            }
        }
    }
}
