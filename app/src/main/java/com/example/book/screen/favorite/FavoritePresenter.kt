package com.example.book.screen.favorite

import com.example.book.data.model.Book
import com.example.book.data.repository.BookRepository
import com.example.book.data.repository.OnResultListener
import java.lang.Exception

class FavoritePresenter(private val repository: BookRepository) : FavoriteContract.Presenter {
    private var view: FavoriteContract.View? = null

    override fun onStart() {
        loadFavoriteBooks()
    }

    override fun onStop() {
    }

    override fun setView(view: FavoriteContract.View?) {
        this.view = checkNotNull(view)
    }

    override fun loadFavoriteBooks() {
        repository.getFavorites(object : OnResultListener<List<Book>> {
            override fun onSuccess(data: List<Book>) {
                view?.onGetBooksSuccess(data)
            }

            override fun onError(exception: Exception) {
                view?.onError(exception)
            }
        })
    }

    override fun addFavoriteBook(book: Book) {
        repository.addToFavorites(book, object : OnResultListener<Book> {
            override fun onSuccess(data: Book) {
                view?.onAddBookToFavorites(book)
                loadFavoriteBooks()
            }

            override fun onError(exception: Exception) {
                view?.onError(exception)
            }
        })
    }

    override fun deleteFavoriteBook(id: Long) {
        repository.deleteFromFavorites(id, object : OnResultListener<String> {
            override fun onSuccess(data: String) {
                view?.onDeleteBookFromFavorites(id)
                loadFavoriteBooks()
            }

            override fun onError(exception: Exception) {
                view?.onError(exception)
            }
        })
    }
}
