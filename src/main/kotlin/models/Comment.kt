package ru.netology.models

data class Comment(
    val id: Int,
    val postId: Int,
    val fromId: Int,
    val date: Int,
    val text: String,
    val donut: Donut? = null,
    val replyToUser: Int? = null,
    val replyToComment: Int? = null,
    val attachments: List<Attachment>? = null,
    val parentsStack: List<Int>? = null,
    val thread: Thread? = null
)

data class Donut(
    val isDon: Boolean,
    val placeholder: String?
)

data class Thread(
    val count: Int,
    val items: List<Comment> = emptyList(),
    val canPost: Boolean,
    val showReplyButton: Boolean,
    val groupsCanPost: Boolean
)
