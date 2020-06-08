package org.amblygon.tolstoy;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BookViewModel extends AndroidViewModel {

    private BookRepository mRepository;
    private LiveData<List<Book>> mAllBooks;
    private LiveData<List<Book>> mReadingBooks;

    public BookViewModel(Application application) {
        super(application);
        mRepository = new BookRepository(application);
        mAllBooks = mRepository.getAllBooks();
        mReadingBooks = mRepository.getReadingBooks();
    }

    LiveData<List<Book>> getAllBooks() {
        return mAllBooks;
    }

    LiveData<List<Book>> getNowReadingBooks() {
        return mReadingBooks;
    }

    void insert(Book book) {
        mRepository.insert(book);
    }

    void updateBook(Book book) {
        mRepository.update(book);
    }
}
