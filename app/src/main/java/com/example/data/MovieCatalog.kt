package com.example.data

data class Movie(
    val id: String,
    val title: String,
    val posterUrl: String,
    val backdropUrl: String,
    val description: String,
    val genre: String,
    val rating: String,
    val releaseYear: String,
    val duration: String,
    val cast: String,
    val matchPercentage: String,
    val category: String
) {
    fun toEntity() = MovieEntity(
        id = id,
        title = title,
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        description = description,
        genre = genre,
        rating = rating,
        releaseYear = releaseYear,
        duration = duration,
        cast = cast
    )
}

object MovieCatalog {
    val heroMovie = Movie(
        id = "midnight_echo",
        title = "THE MIDNIGHT ECHO",
        posterUrl = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?auto=format&fit=crop&w=600&q=80",
        backdropUrl = "https://images.unsplash.com/photo-1536440136628-849c177e76a1?auto=format&fit=crop&w=1200&q=80",
        description = "In the year 2088, an astronaut stranded on an abandoned deep-space relay station starts receiving mysterious, static-heavy transmissions from an unmapped sector of the galaxy that resemble long-lost human memories.",
        genre = "Sci-Fi • Space Mystery • Suspenseful",
        rating = "TV-MA",
        releaseYear = "2026",
        duration = "2h 15m",
        cast = "Alexandra Vance, Caleb Mercer, Dr. Evelyn Thorne",
        matchPercentage = "98% Match",
        category = "Hero"
    )

    val trendingNow = listOf(
        Movie(
            id = "stranger_depths",
            title = "Stranger Depths",
            posterUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?auto=format&fit=crop&w=400&q=80",
            backdropUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?auto=format&fit=crop&w=800&q=80",
            description = "When a deep-sea research submarine vanishes in the Mariana Trench, a group of oceanographers discovers an ancient bioluminescent rift leading to an upside-down aquatic dimension.",
            genre = "Sci-Fi • Thriller • Atmospheric",
            rating = "TV-14",
            releaseYear = "2025",
            duration = "8 Episodes",
            cast = "Winona Harbour, Millie Brown, David Dyer",
            matchPercentage = "95% Match",
            category = "Trending Now"
        ),
        Movie(
            id = "cyber_pulse",
            title = "Cyber Pulse: Neo-Tokyo",
            posterUrl = "https://images.unsplash.com/photo-1542751371-adc38448a05e?auto=format&fit=crop&w=400&q=80",
            backdropUrl = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?auto=format&fit=crop&w=800&q=80",
            description = "A rogue cybernetic hacker runs from a massive megacorporation after stumbling upon a encrypted data file containing the code for digital human consciousness.",
            genre = "Action • Cyberpunk • Mind-Bending",
            rating = "TV-MA",
            releaseYear = "2026",
            duration = "2h 02m",
            cast = "Kenji Sato, Rin Tanaka, Hiroshi Takahashi",
            matchPercentage = "97% Match",
            category = "Trending Now"
        ),
        Movie(
            id = "gothic_whispers",
            title = "Gothic Whispers",
            posterUrl = "https://images.unsplash.com/photo-1509248961158-e54f6934749c?auto=format&fit=crop&w=400&q=80",
            backdropUrl = "https://images.unsplash.com/photo-1518005020951-eccb494ad742?auto=format&fit=crop&w=800&q=80",
            description = "Enrolled in a prestigious boarding school nestled in the eerie woods of New England, a sarcastic gothic teenager uncovers her family's ancient supernatural ties and a monster lurking in the crypts.",
            genre = "Dark Comedy • Mystery • Supernatural",
            rating = "TV-14",
            releaseYear = "2025",
            duration = "10 Episodes",
            cast = "Jenna Ortega, Gwendoline Christie, Hunter Doohan",
            matchPercentage = "92% Match",
            category = "Trending Now"
        ),
        Movie(
            id = "neon_streets",
            title = "Neon Streets",
            posterUrl = "https://images.unsplash.com/photo-1545239351-ef35f43d514b?auto=format&fit=crop&w=400&q=80",
            backdropUrl = "https://images.unsplash.com/photo-1519608487953-e999c86e7455?auto=format&fit=crop&w=800&q=80",
            description = "In the neon-drenched dark alleys of a futuristic city, an elite underground racer is blackmailed into executing the heist of the century using high-speed hover vehicles.",
            genre = "Action • High-Octane • Sci-Fi",
            rating = "PG-13",
            releaseYear = "2024",
            duration = "1h 55m",
            cast = "Ryan Gosling, Ana de Armas, Chris Evans",
            matchPercentage = "89% Match",
            category = "Trending Now"
        )
    )

    val sciFiAndAction = listOf(
        Movie(
            id = "solar_flare",
            title = "Solar Flare",
            posterUrl = "https://images.unsplash.com/photo-1451187580459-43490279c0fa?auto=format&fit=crop&w=400&q=80",
            backdropUrl = "https://images.unsplash.com/photo-1446776811953-b23d57bd21aa?auto=format&fit=crop&w=800&q=80",
            description = "A team of daring scientists embarks on a desperate, high-stakes journey into the center of the solar system to deploy an experimental shield against a solar storm threatening Earth.",
            genre = "Sci-Fi • Thriller • Disaster",
            rating = "PG-13",
            releaseYear = "2025",
            duration = "2h 10m",
            cast = "Cillian Murphy, Rose Byrne, Chris Evans",
            matchPercentage = "94% Match",
            category = "Sci-Fi & Action"
        ),
        Movie(
            id = "apex_hunter",
            title = "Apex Hunter",
            posterUrl = "https://images.unsplash.com/photo-1507679799987-c73779587ccf?auto=format&fit=crop&w=400&q=80",
            backdropUrl = "https://images.unsplash.com/photo-1483168527879-c66136b56105?auto=format&fit=crop&w=800&q=80",
            description = "An ex-special forces operative is recruited to hunt down an escaped, genetically enhanced predator in a pristine but deadly wilderness reserve.",
            genre = "Action • Survival • Adventure",
            rating = "R",
            releaseYear = "2026",
            duration = "1h 48m",
            cast = "Idris Elba, Jessica Chastain, Pedro Pascal",
            matchPercentage = "91% Match",
            category = "Sci-Fi & Action"
        ),
        Movie(
            id = "virtual_rift",
            title = "Virtual Rift",
            posterUrl = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?auto=format&fit=crop&w=400&q=80",
            backdropUrl = "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?auto=format&fit=crop&w=800&q=80",
            description = "When a virtual reality gaming simulation collapses on millions of connected players worldwide, a brilliant software engineer must dive in to defeat a rogue AI warden.",
            genre = "Sci-Fi • Action • Mind-Bending",
            rating = "PG-13",
            releaseYear = "2025",
            duration = "2h 05m",
            cast = "Tye Sheridan, Olivia Cooke, Ben Mendelsohn",
            matchPercentage = "96% Match",
            category = "Sci-Fi & Action"
        ),
        Movie(
            id = "crimson_horizon",
            title = "Crimson Horizon",
            posterUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?auto=format&fit=crop&w=400&q=80",
            backdropUrl = "https://images.unsplash.com/photo-1541185933-ef5d8ed016c2?auto=format&fit=crop&w=800&q=80",
            description = "On the dry, barren red desert of Mars, the inhabitants of the first colony wage an underground war of independence against the authoritarian resource barons of Earth.",
            genre = "Sci-Fi • Action • Political",
            rating = "TV-MA",
            releaseYear = "2026",
            duration = "2h 30m",
            cast = "Florence Pugh, Timothée Chalamet, Zendaya",
            matchPercentage = "98% Match",
            category = "Sci-Fi & Action"
        )
    )

    val animeAndFantasy = listOf(
        Movie(
            id = "spirit_chronicles",
            title = "Spirit Chronicles",
            posterUrl = "https://images.unsplash.com/photo-1578632767115-351597cf2477?auto=format&fit=crop&w=400&q=80",
            backdropUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?auto=format&fit=crop&w=800&q=80",
            description = "A young orphan girl discovers she possesses the rare ancestral ability to communicate with ancient nature spirits, embarking on a quest to save her forest homeland from development.",
            genre = "Anime • Fantasy • Heartwarming",
            rating = "PG",
            releaseYear = "2024",
            duration = "1h 52m",
            cast = "Aoi Yuki, Takahiro Sakurai, Jun Fukuyama",
            matchPercentage = "94% Match",
            category = "Anime & Fantasy"
        ),
        Movie(
            id = "blade_destiny",
            title = "Blade of Destiny",
            posterUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?auto=format&fit=crop&w=400&q=80",
            backdropUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?auto=format&fit=crop&w=800&q=80",
            description = "In a mythical feudal Japan overrun by shape-shifting demons, a skilled samurai fights to cure his cursed sister and avenge his family's honor.",
            genre = "Anime • Fantasy • Action",
            rating = "TV-MA",
            releaseYear = "2025",
            duration = "24 Episodes",
            cast = "Natsuki Hanae, Akari Kito, Hiro Shimono",
            matchPercentage = "98% Match",
            category = "Anime & Fantasy"
        ),
        Movie(
            id = "cloud_realm",
            title = "The Cloud Realm",
            posterUrl = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=400&q=80",
            backdropUrl = "https://images.unsplash.com/photo-1469854523086-cc02fe5d8800?auto=format&fit=crop&w=800&q=80",
            description = "Two teenagers from separate sky cities find an ancient, forbidden aircraft that allows them to descend through the permanent toxic cloud deck to the surface of Earth.",
            genre = "Anime • Adventure • Romance",
            rating = "PG-13",
            releaseYear = "2025",
            duration = "2h 00m",
            cast = "Kano Hara, Takuya Sato, Yui Ishikawa",
            matchPercentage = "90% Match",
            category = "Anime & Fantasy"
        )
    )

    val allMovies = listOf(heroMovie) + trendingNow + sciFiAndAction + animeAndFantasy

    fun searchMovies(query: String): List<Movie> {
        if (query.isEmpty()) return emptyList()
        return allMovies.filter {
            it.title.contains(query, ignoreCase = true) ||
            it.genre.contains(query, ignoreCase = true) ||
            it.description.contains(query, ignoreCase = true) ||
            it.cast.contains(query, ignoreCase = true)
        }
    }
}
