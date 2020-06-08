package org.amblygon.tolstoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.logging.Logger;

public class DetailsActivity extends AppCompatActivity {

    ImageView cover;
    TextView title;
    TextView author;
    TextView publisher;
    TextView description;
    ImageView preview;
    TextView nytcontent;
    ImageView nytclick;
    ImageView google;
    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();

        book = (Book) intent.getSerializableExtra("book");

        cover = findViewById(R.id.cover);
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        publisher = findViewById(R.id.publisher);
        description = findViewById(R.id.description);
        preview = findViewById(R.id.imageView2);
        nytcontent = findViewById(R.id.nyreview);
        nytclick = findViewById(R.id.imageView4);
        google = findViewById(R.id.google);

        String url = book.getCover();
        url = url.substring(4);
        Picasso.with(this).load("https"+url).placeholder(R.drawable.book1).fit().into(cover);

        Log.d("Book-info", book.getTitle());

        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        publisher.setText(book.getPublisher());
        description.setText(book.getDescription());
//        preview.setText(intent.getStringExtra("preview"));

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent impl = new Intent(Intent.ACTION_VIEW, Uri.parse(book.getPreview()));
                startActivity(impl);
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent impl = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q="+book.getTitle()));
                startActivity(impl);
            }
        });

        String nyurl = "https://api.nytimes.com/svc/books/v3/reviews.json?api-key=IImONnq01UQozifjSbHo8qkteZxZ2Zly&isbn=";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, nyurl+book.getIsbn(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject volInfo = response.getJSONArray("results").getJSONObject(0);
                            nytcontent.setText(volInfo.getString("summary"));
                            nytclick.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        Intent impl = new Intent(Intent.ACTION_VIEW, Uri.parse(volInfo.getString("url")));
                                        startActivity(impl);
                                    } catch (Exception e) {
                                    }
                                }
                            });
                        } catch (Exception e) {
                            Log.d("NYR", e.getMessage());
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
    }
}
