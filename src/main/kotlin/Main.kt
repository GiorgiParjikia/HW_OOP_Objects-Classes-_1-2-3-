package ru.netology

import ru.netology.models.*
import ru.netology.services.WallService

fun main() {
    // Создаём новый пост
    val post1 = Post(
        ownerId = 1,
        fromId = 2,
        date = 1678900000,
        text = "Первый пост",
        likes = Likes(count = 5, userLikes = false, canLike = true, canPublish = true),
        reposts = Reposts(count = 0, userReposted = false),
        views = Views(count = 100),
        postType = "post"
    )

    // Добавляем пост
    val addedPost = WallService.add(post1)
    println("Добавленный пост: $addedPost")

    // Обновляем пост (например, меняем текст и лайки)
    val updatedPost = addedPost.copy(text = "Обновлённый пост", likes = Likes(count = 10, userLikes = true, canLike = true, canPublish = true))
    val isUpdated = WallService.update(updatedPost)

    println("Обновление прошло успешно? $isUpdated")
    println("Текущие посты: ${WallService.getAll()}")
}
