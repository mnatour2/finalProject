package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HomeCustomer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_customer);
    }

    public void onViewRoom(View view) {
        Intent search = new Intent(getApplicationContext(), SearchRooms.class);
        startActivity(search);
    }

    public void onViewReservations(View view) {
        Intent mng = new Intent(getApplicationContext(), ManageReservations.class);
        startActivity(mng);
    }

    public void onReserve(View view) {
        Intent reserve = new Intent(getApplicationContext(), ReserveRoom.class);
        startActivity(reserve);
    }

    public void RoomService(View view) {
        Intent intent = new Intent(this , ManageCustomer.class);
        startActivity(intent);
    }



}
