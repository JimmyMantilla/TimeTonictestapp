package com.example.timetonictestapp.domain.landing

import com.example.timetonictestapp.data.datamodels.booksmodels.AllBooks
import com.example.timetonictestapp.data.datamodels.booksmodels.Book
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    suspend fun getAllBooks(sesskey: String, o_u: String) : Flow<AllBooks>
    suspend fun getBookInfo(sesskey: String, o_u: String, b_c: String) : Flow<Book>
}