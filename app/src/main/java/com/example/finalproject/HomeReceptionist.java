package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HomeReceptionist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_receptionist);
    }

    public void RoomStatus(View view) {
        Intent intent = new Intent(getApplicationContext(), AddRoom.class);
        startActivity(intent);

    }


    public void goTomanage(View view) {
        Intent intent = new Intent(getApplicationContext(),ManageCustomer.class);
        startActivity(intent);
    }
}
