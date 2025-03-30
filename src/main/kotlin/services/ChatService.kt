package ru.netology.services

import ru.netology.models.Chat
import ru.netology.models.Message
import java.time.Instant

object ChatService {
    private var chats = mutableListOf<Chat>()
    private var messageIdCounter = 1

    fun clear() {
        chats.clear()
        messageIdCounter = 1
    }

    fun sendMessage(senderId: Int, receiverId: Int, text: String): Message {
        val chat = chats.find { it.userId == receiverId } ?: Chat(receiverId).also { chats.add(it) }
        val message = Message(
            id = messageIdCounter++,
            senderId = senderId,
            receiverId = receiverId,
            text = text,
            timestamp = Instant.now().epochSecond
        )
        chat.messages.add(message)
        return message
    }

    fun getUnreadChatsCount(userId: Int): Int =
        chats.asSequence()
            .filter { chat ->
                chat.messages.any { it.receiverId == userId && !it.read && !it.deleted }
            }
            .count()

    fun getChats(): List<Chat> = chats

    fun getLastMessages(): List<String> =
        chats.asSequence()
            .map { chat ->
                chat.messages.asSequence()
                    .filter { !it.deleted }
                    .lastOrNull()?.text ?: "нет сообщений"
            }
            .toList()

    fun getMessagesFromChat(userId: Int, count: Int): List<Message> =
        chats.find { it.userId == userId }
            ?.messages
            ?.asSequence()
            ?.filter { !it.deleted }
            ?.toList()
            ?.takeLast(count)
            ?.onEach { it.read = true }
            ?: emptyList()

    fun editMessage(messageId: Int, newText: String): Boolean {
        val message = chats.asSequence()
            .flatMap { it.messages.asSequence() }
            .find { it.id == messageId && !it.deleted } ?: return false

        message.text = newText
        return true
    }

    fun deleteMessage(messageId: Int): Boolean {
        val message = chats.asSequence()
            .flatMap { it.messages.asSequence() }
            .find { it.id == messageId } ?: return false

        message.deleted = true
        return true
    }

    fun deleteChat(userId: Int): Boolean =
        chats.removeIf { it.userId == userId }
}