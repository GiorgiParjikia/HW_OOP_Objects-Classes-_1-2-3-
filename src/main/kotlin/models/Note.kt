package ru.netology.models

data class Note(
    val id: Int,
    val title: String,
    val text: String,
    val date: Long,
    var deleted: Boolean = false
)
