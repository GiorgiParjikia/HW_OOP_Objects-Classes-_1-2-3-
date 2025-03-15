package ru.netology.services

class CommentNotFoundException(message: String) : RuntimeException(message)

class InvalidReportReasonException(message: String) : RuntimeException(message)
