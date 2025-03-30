package ru.netology.models

data class Chat(
    val userId: Int,
    val messages: MutableList<Message> = mutableListOf()
)
