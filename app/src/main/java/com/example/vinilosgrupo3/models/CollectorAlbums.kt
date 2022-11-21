package com.example.vinilosgrupo3.models

data class CollectorAlbums (
    val id: Int,
    val price: Int,
    val status: String
)

enum class Status {
    Active, Inactive
}
