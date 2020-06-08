package org.amblygon.tolstoy;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder> {

    class BookViewHolder extends RecyclerView.ViewHolder {
        private final ImageView bookItemView;

        private BookViewHolder(View itemView) {
            super(itemView);
            bookItemView = itemView.findViewById(R.id.cover);
        }
    }

    private final LayoutInflater mInflater;
    private List<Book> mBooks;

    BookListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        if (mBooks != null) {
            Book current = mBooks.get(position);
//            holder.bookItemView.setImageResource(R.drawable.book1);
            String url = current.getCover();
            url = url.substring(4);
            Picasso.with(holder.itemView.getContext()).load("https"+url).placeholder(R.drawable.book1).fit().into(holder.bookItemView);
        }
    }

    void setBooks(List<Book> books) {
        mBooks = books;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mBooks != null)
            return mBooks.size();
        else return 0;
    }

    public Book getBookwithPosition(int position) {
        return mBooks.get(position);
    }
}