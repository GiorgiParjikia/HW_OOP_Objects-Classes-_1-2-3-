package ru.netology.services

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import ru.netology.models.Note
import ru.netology.models.NoteComment

class NoteServiceTest {

    @BeforeEach
    fun clearService() {
        NoteService.clear() // Добавьте метод clear в NoteService, если его ещё нет
    }

    @Test
    fun addNote_ShouldReturnNoteWithId() {
        val note = Note(id = 0, title = "Заголовок", text = "Текст", date = 1742646215)
        val result = NoteService.add(note)

        assertTrue(result.id > 0)
        assertEquals(note.title, result.title)
    }

    @Test
    fun createComment_ShouldSucceed() {
        val note = NoteService.add(Note(id = 0, title = "Title", text = "Text", date = 1742646215))
        val comment = NoteComment(id = 0, noteId = note.id, message = "Комментарий", date = 1742646215)

        val result = NoteService.createComment(note.id, comment)

        assertTrue(result.id > 0)
        assertEquals(note.id, result.noteId)
    }

    @Test
    fun deleteComment_ShouldMarkAsDeleted() {
        val note = NoteService.add(Note(0, "Title", "Text", 1742646215))
        val comment = NoteService.createComment(note.id, NoteComment(0, note.id, "Комментарий", 1742646215))

        val result = NoteService.deleteComment(comment.id)

        assertTrue(result)
    }

    @Test
    fun restoreComment_ShouldRestoreDeletedComment() {
        val note = NoteService.add(Note(0, "Title", "Text", 1742646215))
        val comment = NoteService.createComment(note.id, NoteComment(0, note.id, "Комментарий", 1742646215))

        NoteService.deleteComment(comment.id)
        val result = NoteService.restoreComment(comment.id)

        assertTrue(result)
    }

    @Test
    fun deleteComment_ShouldThrow_WhenAlreadyDeleted() {
        val note = NoteService.add(Note(0, "Title", "Text", 1742646215))
        val comment = NoteService.createComment(note.id, NoteComment(0, note.id, "Комментарий", 1742646215))

        NoteService.deleteComment(comment.id)

        assertThrows<IllegalStateException> {
            NoteService.deleteComment(comment.id)
        }
    }

    @Test
    fun restoreComment_ShouldThrow_WhenNotDeleted() {
        val note = NoteService.add(Note(0, "Title", "Text", 1742646215))
        val comment = NoteService.createComment(note.id, NoteComment(0, note.id, "Комментарий", 1742646215))

        assertThrows<IllegalStateException> {
            NoteService.restoreComment(comment.id)
        }
    }
}
