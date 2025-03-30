package ru.netology.models

data class Message(
    val id: Int,
    val senderId: Int,
    val receiverId: Int,
    var text: String,
    val timestamp: Long,
    var read: Boolean = false,
    var deleted: Boolean = false
)
