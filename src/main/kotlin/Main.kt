package ru.netology

import ru.netology.models.*
import ru.netology.services.WallService

fun main() {
    val photoAttachment = PhotoAttachment(
        id = 1, ownerId = 1,
        photo = Photo(1, 1, "https://vk.com/photo130", "https://vk.com/photo604")
    )

    val videoAttachment = VideoAttachment(
        id = 2, ownerId = 1,
        video = Video(2, 1, "Funny Video", 30)
    )

    val audioAttachment = AudioAttachment(
        id = 3, ownerId = 1,
        audio = Audio(3, 1, "Artist", "Song Title", 180)
    )

    val post = Post(
        ownerId = 1,
        fromId = 2,
        date = 1678900000,
        text = "Пост с вложениями",
        likes = Likes(5, false, true, true),
        reposts = Reposts(0, false),
        views = Views(100),
        attachments = listOf(photoAttachment, videoAttachment, audioAttachment) // Добавили вложения
    )

    val addedPost = WallService.add(post)

    println("Добавленный пост: $addedPost")
}