package org.amblygon.tolstoy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int UPDATE_BOOK = 1;

    private BookViewModel mBookViewModel;

    private JSONObject mResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final BookListAdapter adapter = new BookListAdapter(this);
        recyclerView.setAdapter(adapter);

//        LinearLayoutManager horizontal = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        mBookViewModel = new ViewModelProvider(this).get(BookViewModel.class);

        mBookViewModel.getNowReadingBooks().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable final List<Book> books) {
                adapter.setBooks(books);
            }
        });

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                Book book = adapter.getBookwithPosition(position);
                intent.putExtra("cover", book.getCover());
                intent.putExtra("title", book.getTitle());
                intent.putExtra("author", book.getAuthor());
                intent.putExtra("publisher", book.getPublisher());
                intent.putExtra("description", book.getDescription());
                intent.putExtra("pagecount", book.getPagecount());
                intent.putExtra("preview", book.getPreview());
                intent.putExtra("isbn", book.getIsbn13());
                intent.putExtra("book", book);
//                Toast.makeText(getApplicationContext(), isbn, Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, PictureBarcodeActivity.class);
//                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
                startQRScanner();
            }
        });
    }

    private void startQRScanner() {
        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
            } else {
                updateText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void updateText(String scanCode) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
//        Toast.makeText(this, url+scanCode, Toast.LENGTH_LONG).show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
            (Request.Method.GET, url+scanCode, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    mResponse = response;
                    try {
                        JSONObject volInfo = response.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo");
//                        Toast.makeText(getApplicationContext(), volInfo.toString(), Toast.LENGTH_LONG).show();
                        JSONArray indf = volInfo.getJSONArray("industryIdentifiers");
                        String isbn = indf.getJSONObject(0).getString("identifier");
                        String isbn13 = indf.getJSONObject(1).getString("identifier");
                        String title = volInfo.getString("title");
                        String author = volInfo.getJSONArray("authors").getString(0);
                        String cover = volInfo.getJSONObject("imageLinks").getString("thumbnail");
                        int pagecount = volInfo.getInt("pageCount");
                        String publisher = volInfo.getString("publisher");
                        String description = volInfo.getString("description");
                        String category = volInfo.getJSONArray("categories").getString(0);
                        String preview = volInfo.getString("previewLink");
                        Book book = new Book(isbn, isbn13, title, author, cover, 0, pagecount, publisher, description, category, preview);
//                        Toast.makeText(getApplicationContext(), isbn, Toast.LENGTH_LONG).show();
                        Log.d("Book", "Book with title "+book.getTitle()+" added");
                        mBookViewModel.insert(book);
                    } catch (Exception e) {
                        Log.d("JSON", e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Response", error.toString());
                }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);

//        Toast.makeText(this, mResponse.toString(), Toast.LENGTH_LONG).show();
    }
}

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == UPDATE_BOOK && resultCode == RESULT_OK) {
//            Book word = new Book()
//            mBookViewModel.insert(word);
//        } else {
//            Toast.makeText(
//                    getApplicationContext(),
//                    R.string.empty_not_saved,
//                    Toast.LENGTH_LONG).show();
//        }
//    }
//}