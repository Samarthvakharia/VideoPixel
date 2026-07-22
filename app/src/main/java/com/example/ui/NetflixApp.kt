package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.data.Movie
import com.example.data.MovieCatalog

@Composable
fun NetflixApp(viewModel: NetflixViewModel) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val selectedMovie by viewModel.selectedMovie.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF0A0A0A),
                modifier = Modifier
                    .testTag("bottom_nav")
                    .navigationBarsPadding(),
                windowInsets = WindowInsets(0)
            ) {
                val items = listOf("Home", "My List", "Search", "AI CineMatch")
                val icons = listOf(
                    Icons.Default.Home,
                    Icons.Default.List,
                    Icons.Default.Search,
                    Icons.Default.AutoAwesome
                )

                items.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = {
                            Icon(
                                imageVector = icons[index],
                                contentDescription = title,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                text = title,
                                fontSize = 11.sp,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFFE50914),
                            selectedTextColor = Color(0xFFE50914),
                            unselectedIconColor = Color(0xFFB3B3B3),
                            unselectedTextColor = Color(0xFFB3B3B3),
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        },
        containerColor = Color(0xFF0A0A0A)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            when (selectedTab) {
                0 -> HomeTab(
                    viewModel = viewModel,
                    onMovieClick = { viewModel.selectMovie(it) }
                )
                1 -> MyListTab(
                    viewModel = viewModel,
                    onMovieClick = { viewModel.selectMovie(it) }
                )
                2 -> SearchTab(
                    viewModel = viewModel,
                    onMovieClick = { viewModel.selectMovie(it) }
                )
                3 -> AiCineMatchTab(
                    viewModel = viewModel
                )
            }

            // Interactive Overlays
            selectedMovie?.let { movie ->
                if (isPlaying) {
                    SimulatedPlayerView(
                        movie = movie,
                        viewModel = viewModel,
                        onClose = { viewModel.stopPlayback() }
                    )
                } else {
                    MovieDetailDialog(
                        movie = movie,
                        viewModel = viewModel,
                        onClose = { viewModel.selectMovie(null) },
                        onPlayClick = { viewModel.playMovie(movie) }
                    )
                }
            }
        }
    }
}

// --- TAB 1: HOME TAB ---

@Composable
fun HomeTab(
    viewModel: NetflixViewModel,
    onMovieClick: (Movie) -> Unit
) {
    val scrollState = rememberLazyListState()
    val watchlistIds by viewModel.watchlistIds.collectAsState()
    val isHeroInWatchlist = watchlistIds.contains(MovieCatalog.heroMovie.id)

    LazyColumn(
        state = scrollState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // Top Branded Netflix Header Overlay
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "NETFLIX",
                    color = Color(0xFFE50914),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier.testTag("netflix_brand_logo")
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TV Shows",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { }
                    )
                    Text(
                        text = "Movies",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { }
                    )
                    
                    // Profile picture like in Sophisticated Dark HTML
                    AsyncImage(
                        model = "https://api.dicebear.com/7.x/avataaars/svg?seed=Felix",
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFF0071EB)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        // Hero Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .testTag("hero_banner_card")
                    .clickable { onMovieClick(MovieCatalog.heroMovie) },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF18181B)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Background Backdrop
                    AsyncImage(
                        model = MovieCatalog.heroMovie.backdropUrl,
                        contentDescription = "Hero backdrop",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Cinematic Gradient Shadows (Vignette)
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.4f),
                                        Color.Transparent,
                                        Color(0xFF0A0A0A)
                                    ),
                                    startY = 0f,
                                    endY = 1100f
                                )
                            )
                    )

                    // Hero Content
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(horizontal = 20.dp, vertical = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = MovieCatalog.heroMovie.title,
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center,
                            lineHeight = 34.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = MovieCatalog.heroMovie.genre,
                            color = Color(0xFFA1A1AA),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Play Button
                            Button(
                                onClick = { onMovieClick(MovieCatalog.heroMovie) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .testTag("hero_play_button")
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Play icon",
                                        tint = Color.Black,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Play",
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            // My List Button (Sophisticated Dark backdrop style)
                            Button(
                                onClick = { viewModel.toggleWatchlist(MovieCatalog.heroMovie) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27272A).copy(alpha = 0.85f)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .testTag("hero_watchlist_button")
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = if (isHeroInWatchlist) Icons.Default.Check else Icons.Default.Add,
                                        contentDescription = "Watchlist toggle",
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (isHeroInWatchlist) "Added" else "My List",
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Horizontal Category: Trending Now
        item {
            MovieRowSection(
                title = "Trending Now",
                movies = MovieCatalog.trendingNow,
                onMovieClick = onMovieClick
            )
        }

        // Horizontal Category: Sci-Fi & Action
        item {
            MovieRowSection(
                title = "Sci-Fi & Action Thrillers",
                movies = MovieCatalog.sciFiAndAction,
                onMovieClick = onMovieClick
            )
        }

        // Horizontal Category: Anime & Fantasy
        item {
            MovieRowSection(
                title = "Popular Anime & Fantasy",
                movies = MovieCatalog.animeAndFantasy,
                onMovieClick = onMovieClick
            )
        }
    }
}

@Composable
fun MovieRowSection(
    title: String,
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = title.uppercase(),
            color = MaterialTheme.colorScheme.onSurfaceVariant, // Zinc 400
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(movies) { movie ->
                MovieCard(movie = movie, onClick = { onMovieClick(movie) })
            }
        }
    }
}

@Composable
fun MovieCard(
    movie: Movie,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(110.dp)
            .aspectRatio(0.68f)
            .clickable(onClick = onClick)
            .testTag("movie_card_${movie.id}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = movie.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

// --- TAB 2: MY LIST (ROOM DATABASE WATCHLIST) ---

@Composable
fun MyListTab(
    viewModel: NetflixViewModel,
    onMovieClick: (Movie) -> Unit
) {
    val watchlistEntities by viewModel.watchlist.collectAsState()

    // Map watchlist entities back to Movie domain models
    val watchlistMovies = remember(watchlistEntities) {
        watchlistEntities.map { entity ->
            Movie(
                id = entity.id,
                title = entity.title,
                posterUrl = entity.posterUrl,
                backdropUrl = entity.backdropUrl,
                description = entity.description,
                genre = entity.genre,
                rating = entity.rating,
                releaseYear = entity.releaseYear,
                duration = entity.duration,
                cast = entity.cast,
                matchPercentage = "95% Match", // Default placeholder
                category = "Watchlist"
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "My List",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        if (watchlistMovies.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "Empty watchlist",
                    tint = Color(0xFF564D4D),
                    modifier = Modifier.size(72.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Your Watchlist is Empty",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Explore cinematic contents in Home and save them to My List for convenient offline access later.",
                    color = Color(0xFFB3B3B3),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("watchlist_grid")
            ) {
                items(watchlistMovies) { movie ->
                    Box(
                        modifier = Modifier
                            .aspectRatio(0.68f)
                            .clip(RoundedCornerShape(4.dp))
                            .clickable { onMovieClick(movie) }
                    ) {
                        AsyncImage(
                            model = movie.posterUrl,
                            contentDescription = movie.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        // Quick Delete Icon Layer
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(4.dp)
                                .size(24.dp)
                                .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                                .clickable { viewModel.toggleWatchlist(movie) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.DeleteOutline,
                                contentDescription = "Remove movie",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// --- TAB 3: SEARCH TAB ---

@Composable
fun SearchTab(
    viewModel: NetflixViewModel,
    onMovieClick: (Movie) -> Unit
) {
    val query by viewModel.searchQuery.collectAsState()
    val results by viewModel.searchResults.collectAsState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Search Field
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.updateSearchQuery(it) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("search_field"),
            placeholder = { Text("Search shows, movies, genres...", color = Color(0xFF808080)) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color(0xFFB3B3B3)) },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear search", tint = Color.White)
                    }
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF18181B),
                unfocusedContainerColor = Color(0xFF18181B),
                focusedBorderColor = Color(0xFF27272A),
                unfocusedBorderColor = Color(0xFF18181B),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (query.isEmpty()) {
            // Hot Recommendation Genre Tags
            Text(
                text = "Popular Genres",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            val genres = listOf("Sci-Fi", "Action", "Thriller", "Anime", "Fantasy", "Drama", "Mystery")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(genres) { genre ->
                        Surface(
                            modifier = Modifier
                                .clickable { viewModel.updateSearchQuery(genre) }
                                .testTag("genre_chip_$genre"),
                            color = Color(0xFF27272A),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = genre,
                                color = Color.White,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Default suggestions
            Text(
                text = "Recommended for You",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(MovieCatalog.allMovies.take(5)) { movie ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF18181B), RoundedCornerShape(8.dp))
                            .clickable { onMovieClick(movie) }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = movie.posterUrl,
                            contentDescription = movie.title,
                            modifier = Modifier
                                .width(70.dp)
                                .aspectRatio(1.5f)
                                .clip(RoundedCornerShape(6.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = movie.title,
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { onMovieClick(movie) }) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play icon",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        } else {
            // Filter Results
            Text(
                text = "Top Search Results",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            if (results.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No results found for \"$query\"",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Try typing 'Sci-Fi', 'Action', 'Strange' or search with our CineMatch AI assistant tab!",
                        color = Color(0xFFB3B3B3),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(results) { movie ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF18181B), RoundedCornerShape(8.dp))
                                .clickable { onMovieClick(movie) }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = movie.posterUrl,
                                contentDescription = movie.title,
                                modifier = Modifier
                                    .width(55.dp)
                                    .aspectRatio(0.68f)
                                    .clip(RoundedCornerShape(6.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = movie.title,
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = movie.genre,
                                    color = Color(0xFFB3B3B3),
                                    fontSize = 12.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            IconButton(onClick = { onMovieClick(movie) }) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- TAB 4: AI CINEMATCH TAB (GEMINI API INTEGRATION) ---

@Composable
fun AiCineMatchTab(viewModel: NetflixViewModel) {
    val messages by viewModel.aiChatMessages.collectAsState()
    val isLoading by viewModel.aiChatLoading.collectAsState()
    var inputQuery by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        // App header inside chat
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "AI CineMatch",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Powered by Gemini 3.5 Flash",
                    color = Color(0xFFE50914),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(
                onClick = { viewModel.clearChat() },
                modifier = Modifier.testTag("clear_chat_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Reset chat",
                    tint = Color.White
                )
            }
        }

        // Suggestions/Prompt chips
        val promptSuggestions = listOf(
            "Suggest a sci-fi mystery",
            "What to watch when feeling sad?",
            "Top action shows like Cyberpunk",
            "Give me a gothic suspense series"
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(promptSuggestions) { prompt ->
                Surface(
                    modifier = Modifier
                        .clickable { viewModel.sendAiChatQuery(prompt) }
                        .testTag("suggestion_$prompt"),
                    color = Color(0xFF27272A),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = prompt,
                        color = Color(0xFFE50914),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Message Feed
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            reverseLayout = false
        ) {
            items(messages) { msg ->
                val bubbleBgColor = if (msg.isUser) Color(0xFFE50914) else Color(0xFF18181B)
                val alignment = if (msg.isUser) Alignment.End else Alignment.Start

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = alignment
                ) {
                    Box(
                        modifier = Modifier
                            .widthIn(max = 280.dp)
                            .background(bubbleBgColor, RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = msg.message,
                            color = Color.White,
                            fontSize = 14.sp,
                            lineHeight = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (msg.isUser) "You" else "CineMatch AI",
                        color = Color(0xFF808080),
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }

            if (isLoading) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFFE50914),
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "CineMatch is streaming choices...",
                            color = Color(0xFFB3B3B3),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Chat Entry Field
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputQuery,
                onValueChange = { inputQuery = it },
                placeholder = { Text("What are you in the mood for?", color = Color.Gray, fontSize = 14.sp) },
                modifier = Modifier
                    .weight(1f)
                    .testTag("ai_chat_input"),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = {
                    if (inputQuery.trim().isNotEmpty()) {
                        viewModel.sendAiChatQuery(inputQuery)
                        inputQuery = ""
                        focusManager.clearFocus()
                    }
                }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF18181B),
                    unfocusedContainerColor = Color(0xFF18181B),
                    focusedBorderColor = Color(0xFFE50914),
                    unfocusedBorderColor = Color(0xFF27272A),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    if (inputQuery.trim().isNotEmpty()) {
                        viewModel.sendAiChatQuery(inputQuery)
                        inputQuery = ""
                        focusManager.clearFocus()
                    }
                },
                modifier = Modifier
                    .background(Color(0xFFE50914), CircleShape)
                    .size(44.dp)
                    .testTag("ai_chat_send_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send prompt",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

// --- INTERACTIVE MODAL DETAILED SCREEN ---

@Composable
fun MovieDetailDialog(
    movie: Movie,
    viewModel: NetflixViewModel,
    onClose: () -> Unit,
    onPlayClick: () -> Unit
) {
    val watchlistIds by viewModel.watchlistIds.collectAsState()
    val isAdded = watchlistIds.contains(movie.id)

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A0A0A))
                .statusBarsPadding(),
            color = Color(0xFF0A0A0A)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Large Backdrop
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                ) {
                    AsyncImage(
                        model = movie.backdropUrl,
                        contentDescription = movie.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Close Button
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                            .size(36.dp)
                            .testTag("detail_close_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close overlay",
                            tint = Color.White
                        )
                    }

                    // Bottom Vignette Gradient
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color(0xFF0A0A0A)),
                                    startY = 400f
                                )
                            )
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        // Title
                        Text(
                            text = movie.title,
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.testTag("detail_title")
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Match Metadata Metrics Row
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = movie.matchPercentage,
                                color = Color(0xFF46D369), // Netflix matching green
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = movie.releaseYear,
                                color = Color(0xFFB3B3B3),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )

                            Surface(
                                color = Color(0xFF2C2C2C),
                                shape = RoundedCornerShape(2.dp),
                                modifier = Modifier.padding(vertical = 2.dp)
                            ) {
                                Text(
                                    text = movie.rating,
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }

                            Text(
                                text = movie.duration,
                                color = Color(0xFFB3B3B3),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Large Action Buttons
                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Large Play Trigger
                            Button(
                                onClick = onPlayClick,
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(46.dp)
                                    .testTag("detail_play_button")
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Play",
                                        tint = Color.Black,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Play",
                                        color = Color.Black,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            // Watchlist Add Trigger
                            OutlinedButton(
                                onClick = { viewModel.toggleWatchlist(movie) },
                                shape = RoundedCornerShape(4.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                                border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp, brush = Brush.linearGradient(listOf(Color.Gray, Color.Gray))),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(46.dp)
                                    .testTag("detail_watchlist_button")
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = if (isAdded) Icons.Default.Check else Icons.Default.Add,
                                        contentDescription = "Watchlist add indicator",
                                        tint = Color.White,
                                        modifier = Modifier.size(22.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = if (isAdded) "Added to My List" else "Add to My List",
                                        color = Color.White,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    // Description text
                    item {
                        Text(
                            text = movie.description,
                            color = Color.White,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )
                    }

                    // Cast list text
                    item {
                        Text(
                            text = "Cast: " + movie.cast,
                            color = Color(0xFF808080),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "More Like This",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Horizontal Recommendations List
                    item {
                        val relatedMovies = remember(movie) {
                            MovieCatalog.allMovies.filter { it.category == movie.category && it.id != movie.id }
                                .ifEmpty { MovieCatalog.trendingNow.filter { it.id != movie.id } }
                        }

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(relatedMovies) { related ->
                                Card(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .aspectRatio(0.68f)
                                        .clickable {
                                            viewModel.selectMovie(related)
                                        },
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    AsyncImage(
                                        model = related.posterUrl,
                                        contentDescription = related.title,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

// --- TAB 5: DYNAMIC MEDIA PLAYER SIMULATOR ---

@Composable
fun SimulatedPlayerView(
    movie: Movie,
    viewModel: NetflixViewModel,
    onClose: () -> Unit
) {
    val isPlaying by viewModel.isPlaying.collectAsState()
    val progress by viewModel.playerProgress.collectAsState()
    val isMuted by viewModel.isMuted.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .statusBarsPadding()
    ) {
        // Glowing Screen Background Ambient simulation drawing
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    // Draw a pulsating radial gradient centered to simulate movie lighting
                    val radius = size.minDimension * 0.8f
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFE50914).copy(alpha = 0.25f * (0.6f + progress * 0.4f)),
                                Color(0xFF0071EB).copy(alpha = 0.15f * (1f - progress)),
                                Color.Transparent
                            ),
                            radius = radius
                        )
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Simulating Cinematic Stream visual representation
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .background(Color(0xFF0A0A0A), CircleShape)
                        .drawBehind {
                            drawCircle(
                                color = Color(0xFFE50914),
                                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4.dp.toPx())
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (isPlaying) {
                        CircularProgressIndicator(
                            progress = { progress },
                            color = Color(0xFFE50914),
                            strokeWidth = 4.dp,
                            modifier = Modifier.size(160.dp)
                        )
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Streaming",
                            tint = Color.White,
                            modifier = Modifier.size(64.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Pause,
                            contentDescription = "Paused",
                            tint = Color.White,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Streaming in Ultra HD 4K",
                    color = Color(0xFF46D369),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = movie.title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (isPlaying) "Streaming block..." else "Streaming paused",
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }
        }

        // Top Control Overlay bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                    .testTag("player_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to list",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = movie.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }

        // Bottom HUD Control Panels
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f)),
                        startY = 0f
                    )
                )
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Slider Progression Timeline Row
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val currentMins = (progress * 135).toInt()
                    val totalMins = 135

                    Text(
                        text = "${currentMins / 60}h ${String.format("%02d", currentMins % 60)}m",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Remaining: ${(totalMins - currentMins) / 60}h ${String.format("%02d", (totalMins - currentMins) % 60)}m",
                        color = Color(0xFFB3B3B3),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Slider(
                    value = progress,
                    onValueChange = { viewModel.setProgress(it) },
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFFE50914),
                        activeTrackColor = Color(0xFFE50914),
                        inactiveTrackColor = Color.DarkGray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("player_slider")
                )
            }

            // Interactive Playback Action Triggers Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Rewind 10s
                IconButton(
                    onClick = { viewModel.skipBackward() },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FastRewind,
                        contentDescription = "Skip back 10s",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                // Main Play/Pause
                IconButton(
                    onClick = { viewModel.togglePlayPause() },
                    modifier = Modifier
                        .background(Color.White, CircleShape)
                        .size(56.dp)
                        .testTag("player_play_pause_button")
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Toggle play pause",
                        tint = Color.Black,
                        modifier = Modifier.size(36.dp)
                    )
                }

                // Fast Forward 10s
                IconButton(
                    onClick = { viewModel.skipForward() },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FastForward,
                        contentDescription = "Skip forward 10s",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                // Volume Mute/Unmute
                IconButton(
                    onClick = { viewModel.toggleMute() },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = if (isMuted) Icons.Default.VolumeMute else Icons.Default.VolumeUp,
                        contentDescription = "Toggle Audio stream",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}
