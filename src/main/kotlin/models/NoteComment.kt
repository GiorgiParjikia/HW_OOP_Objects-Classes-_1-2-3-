package ru.netology.models

data class NoteComment(
    val id: Int,
    val noteId: Int,
    val message: String,
    val date: Long,
    var deleted: Boolean = false
)
