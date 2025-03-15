package ru.netology

import ru.netology.models.*
import ru.netology.services.WallService
import ru.netology.services.PostNotFoundException

fun main() {
    println("\n=== Тестируем комментарии ===")

    // Добавляем пост
    val post = WallService.add(
        Post(
            ownerId = 1,
            fromId = 2,
            date = 1678900000,
            text = "Тестовый пост",
            likes = Likes(0, false, true, true),  // Добавляем likes
            reposts = Reposts(0, false)          // Добавляем reposts
        )
    )

    try {
        // Добавляем комментарий к существующему посту
        val comment = WallService.createComment(
            postId = post.id,
            comment = Comment(id = 0, postId = post.id, fromId = 3, date = 1679010000, text = "Первый комментарий")
        )
        println("Комментарий добавлен: $comment")
    } catch (e: PostNotFoundException) {
        println("Ошибка: ${e.message}")
    }

    try {
        // Пытаемся добавить комментарий к несуществующему посту
        WallService.createComment(
            postId = 999,  // Не существующий ID
            comment = Comment(id = 0, postId = 999, fromId = 3, date = 1679010000, text = "Несуществующий пост")
        )
    } catch (e: PostNotFoundException) {
        println("Ошибка: ${e.message}")
    }
}