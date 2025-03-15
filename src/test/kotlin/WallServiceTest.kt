package ru.netology.services

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.netology.models.*
import org.junit.jupiter.api.assertThrows
import ru.netology.services.WallService







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

    @Test
    fun createComment_ShouldAddComment_WhenPostExists() {
        // Создаем пост
        val post = WallService.add(Post(ownerId = 1, fromId = 2, date = 1678900000, text = "Тестовый пост", likes = Likes(0, false, true, true), reposts = Reposts(0, false), views = Views(100)))

        // Добавляем комментарий к существующему посту
        val comment = WallService.createComment(post.id, Comment(id = 0, postId = post.id, fromId = 3, date = 1679010000, text = "Первый комментарий"))

        // Проверяем, что комментарий добавился и id не равен 0
        assertTrue(comment.id > 0)
        assertEquals(post.id, comment.postId)
    }

    @Test
    fun createComment_ShouldThrowException_WhenPostDoesNotExist() {
        assertThrows<PostNotFoundException> {
            WallService.createComment(999, Comment(id = 0, postId = 999, fromId = 3, date = 1679010000, text = "Несуществующий пост"))
        }
    }

    @Test
    fun reportComment_ShouldAddReport_WhenCommentExists() {
        val post = WallService.add(Post(ownerId = 1, fromId = 2, date = 1678900000, text = "Тестовый пост", likes = Likes(0, false, true, true), reposts = Reposts(0, false), views = Views(100)))
        val comment = WallService.createComment(post.id, Comment(id = 0, postId = post.id, fromId = 3, date = 1679010000, text = "Тестовый комментарий"))

        WallService.reportComment(comment.id, comment.fromId, 1) // Причина 1 (детская порнография)

        // Проверяем, что жалоба добавлена
        assertDoesNotThrow { WallService.reportComment(comment.id, comment.fromId, 1) }
    }

    @Test
    fun reportComment_ShouldThrowException_WhenCommentDoesNotExist() {
        assertThrows<CommentNotFoundException> {
            WallService.reportComment(999, 1, 2) // Не существующий комментарий
        }
    }

    @Test
    fun reportComment_ShouldThrowException_WhenReasonIsInvalid() {
        val post = WallService.add(Post(ownerId = 1, fromId = 2, date = 1678900000, text = "Тестовый пост", likes = Likes(0, false, true, true), reposts = Reposts(0, false), views = Views(100)))
        val comment = WallService.createComment(post.id, Comment(id = 0, postId = post.id, fromId = 3, date = 1679010000, text = "Тестовый комментарий"))

        assertThrows<InvalidReportReasonException> {
            WallService.reportComment(comment.id, comment.fromId, 99) // Некорректная причина
        }
    }


}



