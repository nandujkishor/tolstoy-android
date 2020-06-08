package org.amblygon.tolstoy;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;

class BookRepository {

    private BookDao mBookDao;
    private LiveData<List<Book>> mAllBooks;
    private LiveData<List<Book>> mReadingBooks;

    BookRepository(Application application) {
        BookRoomDatabase db = BookRoomDatabase.getDatabase(application);
        mBookDao = db.bookDao();
        mAllBooks = mBookDao.getAllBooks();
        mReadingBooks = mBookDao.getReadingBooks();

    }

    LiveData<List<Book>> getAllBooks() {
        return mAllBooks;
    }

    LiveData<List<Book>> getReadingBooks() {
        return mReadingBooks;
    }

    void insert(Book book) {
        BookRoomDatabase.databaseWriteExecutor.execute(() -> {
            mBookDao.insert(book);
        });
    }

    void update(Book book) {
        BookRoomDatabase.databaseWriteExecutor.execute(() -> {
            mBookDao.updateBook(book);
        });
    }
}
