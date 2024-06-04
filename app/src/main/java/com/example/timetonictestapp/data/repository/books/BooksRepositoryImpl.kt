package com.example.timetonictestapp.data.repository.books

import com.example.timetonictestapp.data.RetrofitService
import com.example.timetonictestapp.data.datamodels.booksmodels.AllBooks
import com.example.timetonictestapp.data.datamodels.booksmodels.Book
import com.example.timetonictestapp.domain.landing.BooksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class BooksRepositoryImpl(val service: RetrofitService) : BooksRepository {
    override suspend fun getAllBooks(sesskey: String, o_u: String): Flow<AllBooks> {
        val remoteResultBooks = service.getAllBooks(sesskey = sesskey, o_u = o_u, u_c = o_u)
        return flowOf(remoteResultBooks.allBooks)
    }

    override suspend fun getBookInfo(sesskey: String, o_u: String, b_c: String): Flow<Book> {
        val remoteResultBook = service.getBookInfo(
            sesskey = sesskey, o_u = o_u, u_c = o_u, b_c = b_c, b_o = o_u
        )
        return flowOf(remoteResultBook.book)
    }
}