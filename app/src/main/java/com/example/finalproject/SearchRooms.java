package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.example.finalproject.RecycleAdapter;
import com.example.finalproject.Datamodel.ItemDataModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchRooms extends AppCompatActivity {

    private RecycleAdapter adapter;
    private ArrayList<ItemDataModel> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rooms);

        fillExampleList();
        setUpRecyclerView();
    }

    private void fillExampleList() {
        dataSet = new ArrayList<>();
        dataSet.add(new ItemDataModel(R.drawable.room1, "luxury room one"));
        dataSet.add(new ItemDataModel(R.drawable.room2, "presidential suite"));
        dataSet.add(new ItemDataModel(R.drawable.room3, "kingpin room"));
        dataSet.add(new ItemDataModel(R.drawable.room4, "luxury room two"));
        dataSet.add(new ItemDataModel(R.drawable.room5, "comfort family room"));
    }
    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new RecycleAdapter(dataSet);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Log.d("newText1",query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("newText",newText);

                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}