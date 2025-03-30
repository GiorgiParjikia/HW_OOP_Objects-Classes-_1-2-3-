package ru.netology

import ru.netology.services.ChatService

fun main() {
    println("=== ChatService Demo ===")

    // Очищаем данные (на случай повторного запуска)
    ChatService.clear()

    // Пользователь 1 пишет пользователю 2
    ChatService.sendMessage(senderId = 1, receiverId = 2, text = "Привет!")
    ChatService.sendMessage(senderId = 1, receiverId = 2, text = "Как дела?")

    // Пользователь 2 отвечает
    ChatService.sendMessage(senderId = 2, receiverId = 1, text = "Привет, всё хорошо!")

    // Получаем список всех чатов
    val chats = ChatService.getChats()
    println("Чаты: ${chats.map { it.userId }}")

    // Проверим, сколько чатов содержит непрочитанные сообщения для userId = 2
    val unreadCount = ChatService.getUnreadChatsCount(userId = 2)
    println("Непрочитанных чатов для пользователя 2: $unreadCount")

    // Получаем последние сообщения из каждого чата
    val lastMessages = ChatService.getLastMessages()
    println("Последние сообщения: $lastMessages")

    // Получаем 10 последних сообщений из чата с userId = 2
    val messages = ChatService.getMessagesFromChat(userId = 2, count = 10)
    println("Сообщения в чате с пользователем 2:")
    messages.forEach { println("- ${it.text} (прочитано: ${it.read})") }

    // Редактируем сообщение
    val firstMessageId = messages.firstOrNull()?.id
    if (firstMessageId != null) {
        val edited = ChatService.editMessage(messageId = firstMessageId, newText = "Привет, друг!")
        println("Сообщение отредактировано: $edited")
    }

    // Удаляем одно из сообщений
    val deleted = firstMessageId?.let { ChatService.deleteMessage(it) } ?: false
    println("Сообщение удалено: $deleted")

    // Удаляем весь чат
    val chatDeleted = ChatService.deleteChat(userId = 2)
    println("Чат с пользователем 2 удалён: $chatDeleted")
}
