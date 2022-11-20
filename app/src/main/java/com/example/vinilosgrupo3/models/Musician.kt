package com.example.vinilosgrupo3.models

data class Musician (
    val id:Int,
    val name:String,
    val image:String,
    val description:String,
    val birthDate:String,
    val albums: List <Album>
    )