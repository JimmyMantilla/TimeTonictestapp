package com.example.timetonictestapp.data.datamodels.booksmodels

data class RemoteResultBooks(
    val allBooks: AllBooks,
    val createdVNB: String,
    val req: String,
    val sstamp: Long,
    val status: String
)