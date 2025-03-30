package ru.netology.services

import ru.netology.models.Chat
import ru.netology.models.Message
import java.time.Instant

object ChatService {
    private var chats = mutableListOf<Chat>()
    private var messageIdCounter = 1

    fun sendMessage(senderId: Int, receiverId: Int, text: String): Message {
        val chat = chats.find { it.userId == receiverId } ?: Chat(receiverId).also { chats.add(it) }
        val message = Message(id = messageIdCounter++, senderId = senderId, receiverId = receiverId, text = text, timestamp = Instant.now().epochSecond)
        chat.messages.add(message)
        return message
    }

    fun getUnreadChatsCount(userId: Int): Int =
        chats.count { it.messages.any { msg -> msg.receiverId == userId && !msg.read && !msg.deleted } }

    fun getChats(): List<Chat> = chats

    fun getLastMessages(): List<String> = chats.map {
        it.messages.filter { !it.deleted }.lastOrNull()?.text ?: "нет сообщений"
    }

    fun getMessagesFromChat(userId: Int, count: Int): List<Message> {
        val chat = chats.find { it.userId == userId } ?: return emptyList()
        val messages = chat.messages.filter { !it.deleted }.takeLast(count)
        messages.forEach { it.read = true }
        return messages
    }

    fun editMessage(messageId: Int, newText: String): Boolean {
        val message = chats.flatMap { it.messages }.find { it.id == messageId && !it.deleted } ?: return false
        message.text = newText
        return true
    }

    fun deleteMessage(messageId: Int): Boolean {
        val message = chats.flatMap { it.messages }.find { it.id == messageId } ?: return false
        message.deleted = true
        return true
    }

    fun deleteChat(userId: Int): Boolean = chats.removeIf { it.userId == userId }

    fun clear() {
        chats.clear()
        messageIdCounter = 1
    }
}
