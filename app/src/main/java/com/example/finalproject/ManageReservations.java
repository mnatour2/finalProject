package com.example.finalproject;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.finalproject.Datamodel.CustomAdapterManageRes;

public class ManageReservations extends Activity {

    ListView simpleList;
    String descriptions[] = {"room1"};
    int images[] = {R.drawable.room1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_reservations);
        simpleList = (ListView) findViewById(R.id.simpleListView);
        CustomAdapterManageRes customAdapter = new CustomAdapterManageRes(getApplicationContext(), descriptions, images);
        simpleList.setAdapter(customAdapter);

        //test code that works, build on this to change data depending on user decisions
        descriptions = new String[]{"room1", "room2"};
        images = new int[]{R.drawable.room1, R.drawable.room2};
        customAdapter.setNewData(images, descriptions);
        customAdapter.notifyDataSetChanged();
    }
}