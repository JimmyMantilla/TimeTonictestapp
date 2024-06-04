package com.example.timetonictestapp.data.datamodels.booksmodels

data class RemoteResultBook(
    val status: String,
    val sstamp: String,
    val book: Book,
    val createdVNB: String,
    val req: String
)
