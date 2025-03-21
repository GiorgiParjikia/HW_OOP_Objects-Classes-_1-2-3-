package ru.netology.services

import ru.netology.models.Post
import ru.netology.models.Comment
import ru.netology.models.*

object WallService {
    private var posts = mutableListOf<Post>()
    private var comments = mutableListOf<Comment>()
    private var reports = mutableListOf<Report>()
    private var nextPostId = 1
    private var nextCommentId = 1

    fun clear() {
        posts.clear()
        comments.clear()
        reports.clear()
        nextPostId = 1
        nextCommentId = 1
    }

    fun add(post: Post): Post {
        val newPost = post.copy(id = nextPostId++)
        posts.add(newPost)
        return newPost
    }

    fun update(post: Post): Boolean {
        for ((index, existingPost) in posts.withIndex()) {
            if (existingPost.id == post.id) {
                posts[index] = existingPost.copy(
                    text = post.text ?: existingPost.text,
                    comments = post.comments ?: existingPost.comments,
                    views = post.views ?: existingPost.views,
                    postType = post.postType ?: existingPost.postType
                )
                return true
            }
        }
        return false
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        val post = posts.find { it.id == postId }
            ?: throw PostNotFoundException("Post with ID $postId not found")

        val newComment = comment.copy(id = nextCommentId++, postId = postId)
        comments.add(newComment)
        return newComment
    }

    fun reportComment(commentId: Int, ownerId: Int, reason: Int) {
        val comment = comments.find { it.id == commentId }
            ?: throw CommentNotFoundException("Comment with ID $commentId not found")

        if (reason !in 0..8) {
            throw InvalidReportReasonException("Invalid report reason: $reason")
        }

        reports.add(Report(commentId, ownerId, reason))
    }

    fun getAll(): List<Post> = posts
}