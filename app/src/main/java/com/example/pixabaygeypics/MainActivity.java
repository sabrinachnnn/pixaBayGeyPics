package com.example.pixabaygeypics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerview;
    private EditText searchtext;
    private Button search;
    private RequestQueue requestQueue;
    private List<Item> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        search = findViewById(R.id.search);
        searchtext = findViewById(R.id.searchtext);


        requestQueue = VolleySingleton.getInstance(this).getRequestQueue();

        mList = new ArrayList<>();

        search.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String text = String.valueOf(searchtext.getText());
                String url = "https://pixabay.com/api/?key=38229833-3a78da00d70f7efc4f1edc091&q="+text+"&image_type=photo&pretty=true";
                mList.clear();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");

                            for (int i = 0; i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String imageURL = jsonObject.getString("webformatURL");
                                int likes = jsonObject.getInt("likes");
                                Item post = new Item(imageURL, likes);
                                mList.add(post);

                            }
                            postAdapter adapter = new postAdapter(MainActivity.this, mList);
                            recyclerview.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                requestQueue.add(jsonObjectRequest);
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                itemTouchHelper.attachToRecyclerView(recyclerview);
            }

            ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                    int fromPosition = viewHolder.getAdapterPosition();
                    int toPosition = target.getAdapterPosition();

                    Collections.swap(mList, fromPosition, toPosition);
                    recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                }
            };
        });
}


        //fetchData();


    private void fetchData(){
        String url = "https://pixabay.com/api/?key=38229833-3a78da00d70f7efc4f1edc091&q=yellow+flowers&image_type=photo&pretty=true";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");

                    for (int i = 0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String imageURL = jsonObject.getString("webformatURL");
                        int likes = jsonObject.getInt("likes");

                        Item post = new Item(imageURL, likes);
                        mList.add(post);

                    }
                    postAdapter adapter = new postAdapter(MainActivity.this, mList);
                    recyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }
}