package ru.netology.models

// Sealed-класс для вложений
sealed class Attachment(val type: String)

// Фото
data class PhotoAttachment(val photo: Photo) : Attachment("photo")

data class Photo(
    val id: Int,
    val ownerId: Int,
    val photo130: String,
    val photo604: String
)

// Видео
data class VideoAttachment(val video: Video) : Attachment("video")

data class Video(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val duration: Int
)

// Аудио
data class AudioAttachment(val audio: Audio) : Attachment("audio")

data class Audio(
    val id: Int,
    val ownerId: Int,
    val artist: String,
    val title: String,
    val duration: Int
)

// Документ
data class DocAttachment(val doc: Doc) : Attachment("doc")

data class Doc(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val size: Int,
    val ext: String
)

// Ссылка
data class LinkAttachment(val link: Link) : Attachment("link")

data class Link(
    val url: String,
    val title: String,
    val description: String
)