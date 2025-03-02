package ru.netology.models

data class Post(
    val id: Int = 0,
    val ownerId: Int,
    val fromId: Int,
    val date: Int,
    val text: String,
    val friendsOnly: Boolean = false,
    val likes: Likes,
    val reposts: Reposts,
    val views: Views,
    val postType: String
)
