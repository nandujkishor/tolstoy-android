package org.amblygon.tolstoy;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "book_table")
public class Book implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "isbn")
    private String mIsbn;
    @NonNull
    @ColumnInfo(name = "isbn13")
    private String mIsbn13;
    @NonNull
    @ColumnInfo(name = "title")
    private String mTitle;
    @NonNull
    @ColumnInfo(name = "author")
    private String mAuthor;
    @NonNull
    @ColumnInfo(name = "cover")
    private String mCover;
    @NonNull
    @ColumnInfo(name="progress")
    private int mProgress;
    @NonNull
    @ColumnInfo(name = "pagecount")
    private int mPagecount;
    @NonNull
    @ColumnInfo(name = "publisher")
    private String mPublisher;
    @NonNull
    @ColumnInfo(name="description")
    private String mDescription;
    @NonNull
    @ColumnInfo(name="category")
    private String mCategory;
    @NonNull
    @ColumnInfo(name="preview")
    private String mPreview;

    public Book(@NonNull String isbn, @NonNull String isbn13, @NonNull String title, @NonNull String author, @NonNull String cover, @NonNull int progress, @NonNull int pagecount, @NonNull String publisher, @NonNull String description, @NonNull String category, @NonNull String preview) {
        this.mIsbn = isbn;
        this.mIsbn13 = isbn13;
        this.mTitle = title;
        this.mAuthor = author;
        this.mCover = cover;
        this.mProgress = progress;
        this.mPagecount = pagecount;
        this.mPublisher = publisher;
        this.mDescription = description;
        this.mCategory = category;
        this.mPreview = preview;
    }

    @NonNull
    public String getIsbn() {
        return this.mIsbn;
    }

    @NonNull
    public String getIsbn13() {
        return this.mIsbn13;
    }

    @NonNull
    public String getTitle() {
        return this.mTitle;
    }

    @NonNull
    public String getAuthor() {
        return this.mAuthor;
    }

    @NonNull
    public String getCover() {
        return this.mCover;
    }

    @NonNull
    public int getProgress() {
        return this.mProgress;
    }

    @NonNull
    public int getPagecount() {
        return this.mPagecount;
    }

    @NonNull
    public String getPublisher() {
        return this.mPublisher;
    }

    @NonNull
    public String getDescription() {
        return this.mDescription;
    }

    @NonNull
    public String getCategory() {
        return this.mCategory;
    }

    @NonNull
    public String getPreview() {
        return this.mPreview;
    }
}
