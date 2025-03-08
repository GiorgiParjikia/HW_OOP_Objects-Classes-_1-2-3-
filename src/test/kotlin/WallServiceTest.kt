package ru.netology.services

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.netology.models.*

class WallServiceTest {

    @BeforeEach
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun addPost_ShouldAssignUniqueId() {
        val post = Post(
            ownerId = 1, fromId = 2, date = 1678900000,
            likes = Likes(0, false, true, true),
            reposts = Reposts(0, false)
        ) // Не передаём text, comments, views, postType

        val addedPost = WallService.add(post)

        assertTrue(addedPost.id > 0)
    }

    @Test
    fun updatePost_ShouldNotOverrideWithNull() {
        val post = WallService.add(Post(
            ownerId = 1, fromId = 2, date = 1678900000, text = "Оригинальный текст",
            likes = Likes(5, false, true, true),
            reposts = Reposts(0, false),
            views = Views(100)
        ))

        val updatedPost = post.copy(text = null, views = null) // Передаём null
        WallService.update(updatedPost)

        val resultPost = WallService.getAll().first()

        assertEquals("Оригинальный текст", resultPost.text) // Проверяем, что текст не затёрся
        assertNotNull(resultPost.views) // Проверяем, что views остались
    }
}