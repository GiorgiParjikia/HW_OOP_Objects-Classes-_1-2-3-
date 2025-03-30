package ru.netology.services

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

class ChatServiceTest {

    @BeforeEach
    fun clearData() {
        ChatService.clear()
    }

    @Test
    fun sendMessage_ShouldCreateChatAndStoreMessage() {
        ChatService.sendMessage(senderId = 1, receiverId = 2, text = "Привет!")
        val chats = ChatService.getChats()
        assertEquals(1, chats.size)
        assertEquals(1, ChatService.getMessagesFromChat(2, 10).size)
    }

    @Test
    fun getUnreadChatsCount_ShouldReturnCorrectCount() {
        ChatService.sendMessage(senderId = 1, receiverId = 2, text = "Сообщение 1")
        ChatService.sendMessage(senderId = 1, receiverId = 3, text = "Сообщение 2")
        assertEquals(2, ChatService.getUnreadChatsCount(2) + ChatService.getUnreadChatsCount(3))
    }

    @Test
    fun deleteMessage_ShouldRemoveMessageFromChat() {
        val message = ChatService.sendMessage(senderId = 1, receiverId = 2, text = "Удаляемое")
        ChatService.deleteMessage(message.id)
        val messages = ChatService.getMessagesFromChat(2, 10)
        assertEquals(0, messages.size)
    }

    @Test
    fun deleteChat_ShouldRemoveEntireChat() {
        ChatService.sendMessage(senderId = 1, receiverId = 2, text = "Удалить чат")
        val deleted = ChatService.deleteChat(2)
        assertTrue(deleted)
        assertEquals(0, ChatService.getChats().size)
    }

    @Test
    fun getLastMessages_ShouldReturnCorrectText() {
        ChatService.sendMessage(senderId = 1, receiverId = 2, text = "Последнее сообщение")
        val lastMessages = ChatService.getLastMessages()
        assertEquals("Последнее сообщение", lastMessages.first())
    }

    @Test
    fun editMessage_ShouldUpdateMessageText() {
        val message = ChatService.sendMessage(1, 2, "Редактируемое")
        val updated = ChatService.editMessage(message.id, "Обновлено")
        assertTrue(updated)
        val result = ChatService.getMessagesFromChat(2, 10).first().text
        assertEquals("Обновлено", result)
    }

    @Test
    fun getMessagesFromChat_ShouldMarkMessagesAsRead() {
        ChatService.sendMessage(1, 2, "Для чтения")
        val messages = ChatService.getMessagesFromChat(2, 10)
        assertTrue(messages.all { it.read })
    }
    @Test
    fun editMessage_ShouldReturnFalse_IfMessageNotFound() {
        val result = ChatService.editMessage(999, "Текст")
        assertFalse(result)
    }

    @Test
    fun deleteMessage_ShouldReturnFalse_IfMessageNotFound() {
        val result = ChatService.deleteMessage(999)
        assertFalse(result)
    }

}
