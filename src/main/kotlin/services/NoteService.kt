package ru.netology.services

import ru.netology.models.Note
import ru.netology.models.NoteComment

object NoteService {
    private var notes = mutableListOf<Note>()
    private var comments = mutableListOf<NoteComment>()
    private var nextNoteId = 1
    private var nextCommentId = 1

    fun add(note: Note): Note {
        val newNote = note.copy(id = nextNoteId++)
        notes.add(newNote)
        return newNote
    }

    fun createComment(noteId: Int, comment: NoteComment): NoteComment {
        val note = notes.find { it.id == noteId && !it.deleted }
            ?: throw IllegalArgumentException("Note not found or deleted")

        val newComment = comment.copy(id = nextCommentId++, noteId = noteId)
        comments.add(newComment)
        return newComment
    }

    fun delete(noteId: Int): Boolean {
        val note = notes.find { it.id == noteId } ?: return false
        note.deleted = true
        return true
    }

    fun deleteComment(commentId: Int): Boolean {
        val comment = comments.find { it.id == commentId } ?: return false
        if (comment.deleted) throw IllegalStateException("Comment already deleted")
        comment.deleted = true
        return true
    }

    fun restoreComment(commentId: Int): Boolean {
        val comment = comments.find { it.id == commentId } ?: return false
        if (!comment.deleted) throw IllegalStateException("Comment is not deleted")
        comment.deleted = false
        return true
    }

    fun get(): List<Note> = notes.filter { !it.deleted }

    fun getById(id: Int): Note? = notes.find { it.id == id && !it.deleted }

    fun getComments(noteId: Int): List<NoteComment> =
        comments.filter { it.noteId == noteId && !it.deleted }

    fun edit(noteId: Int, title: String, text: String): Boolean {
        val index = notes.indexOfFirst { it.id == noteId && !it.deleted }
        if (index == -1) return false

        val oldNote = notes[index]
        notes[index] = oldNote.copy(title = title, text = text)
        return true
    }


    fun editComment(commentId: Int, message: String): Boolean {
        val index = comments.indexOfFirst { it.id == commentId && !it.deleted }
        if (index == -1) return false

        val updatedComment = comments[index].copy(message = message)
        comments[index] = updatedComment
        return true
    }

    fun clear() {
        notes.clear()
        comments.clear()
        nextNoteId = 1
        nextCommentId = 1
    }

}