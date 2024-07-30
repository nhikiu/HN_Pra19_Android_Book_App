package com.example.book.data.repository.source.local.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.book.data.model.Book

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "favorites.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "favorites"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_RATING = "rating"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = (
            "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_TITLE TEXT," +
                "$COLUMN_AUTHOR TEXT," +
                "$COLUMN_DESCRIPTION TEXT," +
                "$COLUMN_IMAGE TEXT," +
                "$COLUMN_RATING REAL)"
        )
        db.execSQL(createTable)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addFavorite(book: Book) {
        val db = this.writableDatabase
        val values =
            ContentValues().apply {
                put(COLUMN_TITLE, book.title)
                put(COLUMN_AUTHOR, book.author)
                put(COLUMN_DESCRIPTION, book.description)
                put(COLUMN_IMAGE, book.image)
                put(COLUMN_RATING, book.rating)
            }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun deleteFavorite(id: Long) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
    }

    fun getAllFavorites(): List<Book> {
        val favoritesList = mutableListOf<Book>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val book =
                    Book(
                        id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR)),
                        description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                        image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)),
                        rating = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_RATING)),
                    )
                favoritesList.add(book)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return favoritesList
    }
}
