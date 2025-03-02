package ru.netology.services

import ru.netology.models.Post

object WallService {
    private var posts = mutableListOf<Post>()
    private var nextId = 1

    fun clear() {
        posts.clear()
        nextId = 1
    }

    fun add(post: Post): Post {
        val newPost = post.copy(id = nextId++)
        posts.add(newPost)
        return newPost
    }

    fun update(post: Post): Boolean {
        for ((index, existingPost) in posts.withIndex()) {
            if (existingPost.id == post.id) {
                posts[index] = post.copy(id = existingPost.id) // Обновляем, но сохраняем id
                return true
            }
        }
        return false
    }

    fun getAll(): List<Post> = posts
}