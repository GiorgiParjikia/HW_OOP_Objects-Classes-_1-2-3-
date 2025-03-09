package ru.netology.services

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.netology.models.*

class WallServiceTest {

    @BeforeEach
    fun clearBeforeTest() {
        WallService.clear() // Очищаем перед каждым тестом
    }

    @Test
    fun addPost_ShouldAssignUniqueId() {
        val post = Post(
            ownerId = 1, fromId = 2, date = 1678900000,
            text = "Первый пост",
            likes = Likes(0, false, true, true),
            reposts = Reposts(0, false),
            views = Views(100)
        )

        val addedPost = WallService.add(post)

        assertTrue(addedPost.id > 0) // Проверяем, что id назначен
    }

    @Test
    fun updatePost_ShouldReturnTrue_WhenPostExists() {
        val post = WallService.add(Post(
            ownerId = 1, fromId = 2, date = 1678900000, text = "Оригинальный текст",
            likes = Likes(5, false, true, true),
            reposts = Reposts(0, false),
            views = Views(100)
        ))

        val updatedPost = post.copy(text = "Обновленный текст")
        val result = WallService.update(updatedPost)

        assertTrue(result) // Проверяем, что обновление успешно
    }

    @Test
    fun updatePost_ShouldReturnFalse_WhenPostDoesNotExist() {
        val fakePost = Post(
            id = 999, ownerId = 1, fromId = 2, date = 1678900000, text = "Фейковый пост",
            likes = Likes(0, false, true, true),
            reposts = Reposts(0, false),
            views = Views(0)
        )

        val result = WallService.update(fakePost)

        assertFalse(result) // Проверяем, что обновление не произошло
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