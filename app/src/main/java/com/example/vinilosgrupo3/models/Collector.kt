package com.example.vinilosgrupo3.models

import org.json.JSONArray

data class Collector(
    val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val comments: List<Comment>,
    val favoritePerformers: List<Musician>,
    val collectorAlbums: List<CollectorAlbums>
)