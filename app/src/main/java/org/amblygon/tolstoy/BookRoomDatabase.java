package org.amblygon.tolstoy;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Book.class}, version = 1, exportSchema = false)
abstract class BookRoomDatabase extends RoomDatabase {

    abstract BookDao bookDao();

    private static volatile BookRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static BookRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BookRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BookRoomDatabase.class, "book_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                BookDao dao = INSTANCE.bookDao();
//                dao.deleteAll();

//                Book book = new Book("123", "1234", "Master of Go", "Yas Kawa", "http://books.google.com/books/content?id=kdbPXB5jW-4C&printsec=frontcover&img=1&zoom=1&source=gbs_api", 64, 218, "Prhipel", "Wonderful book with an attitude", "Game", "https://google.com");
//                dao.insert(book);
//                book = new Book("456", "7890", "The Library Book", "Susan Orlean", "http://books.google.com/books/content?id=l-esDwAAQBAJ&printsec=frontcover&img=1&zoom=1", 148, 312,"Prhipel", "Wonderful book with an attitude", "Game", "https://google.com");
//                dao.insert(book);
//                book = new Book("9353050200", "7890", "The Most Dangerous Place", "Srinath Raghavan", "http://books.google.com/books/content?id=Wx1eDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api", 0, 500,"Prhipel", "Wonderful book with an attitude", "Game", "https://google.com");
//                dao.insert(book);
            });
        }
    };
}
