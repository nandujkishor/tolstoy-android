package org.amblygon.tolstoy;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {

    @Query("SELECT * from book_table ORDER BY title ASC")
    LiveData<List<Book>> getAllBooks();

    @Query("SELECT * from book_table WHERE progress != pagecount ORDER BY title ASC")
    LiveData<List<Book>> getReadingBooks();

    @Query("SELECT * from book_table WHERE isbn = :isbn LIMIT 1")
    Book getItemByIsbn(String isbn);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Book book);

    @Update
    public void updateBook(Book book);

    @Query("DELETE FROM book_table")
    void deleteAll();
}
