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
            ownerId = 1, fromId = 2, date = 1678900000, text = "Тестовый пост",
            likes = Likes(0, false, true, true),
            reposts = Reposts(0, false),
            views = Views(0),
            postType = "post"
        )

        val addedPost = WallService.add(post)

        assertTrue(addedPost.id > 0)
    }

    @Test
    fun updatePost_ShouldReturnTrue_WhenPostExists() {
        val post = WallService.add(Post(
            ownerId = 1, fromId = 2, date = 1678900000, text = "Тест",
            likes = Likes(0, false, true, true),
            reposts = Reposts(0, false),
            views = Views(0),
            postType = "post"
        ))

        val updatedPost = post.copy(text = "Обновленный текст")
        val result = WallService.update(updatedPost)

        assertTrue(result)
    }

    @Test
    fun updatePost_ShouldReturnFalse_WhenPostDoesNotExist() {
        val fakePost = Post(
            id = 999, ownerId = 1, fromId = 2, date = 1678900000, text = "Фейковый пост",
            likes = Likes(0, false, true, true),
            reposts = Reposts(0, false),
            views = Views(0),
            postType = "post"
        )

        val result = WallService.update(fakePost)

        assertFalse(result)
    }
}
