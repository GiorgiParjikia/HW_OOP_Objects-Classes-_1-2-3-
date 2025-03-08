package ru.netology

import ru.netology.models.*
import ru.netology.services.WallService

fun main() {
    val attachments: List<Attachment> = listOf(
        PhotoAttachment(Photo(1, 1, "https://vk.com/photo130", "https://vk.com/photo604")),
        VideoAttachment(Video(2, 1, "Funny Video", 30)),
        AudioAttachment(Audio(3, 1, "Artist", "Song Title", 180))
    )

    for (attachment in attachments) {
        when (attachment) {
            is PhotoAttachment -> println("Фото: ${attachment.photo.photo130}")
            is VideoAttachment -> println("Видео: ${attachment.video.title}")
            is AudioAttachment -> println("Аудио: ${attachment.audio.artist} - ${attachment.audio.title}")
            is DocAttachment -> println("Документ: ${attachment.doc.title}")
            is LinkAttachment -> println("Ссылка: ${attachment.link.url}")
        }
    }
}