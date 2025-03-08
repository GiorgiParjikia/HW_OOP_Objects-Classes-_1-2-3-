package ru.netology.models

data class Post(
    val id: Int = 0,
    val ownerId: Int,
    val fromId: Int,
    val date: Int,
    val text: String? = null,
    val friendsOnly: Boolean = false,
    val comments: Comments? = null,
    val likes: Likes,
    val reposts: Reposts,
    val views: Views? = null,
    val postType: String? = null
)

