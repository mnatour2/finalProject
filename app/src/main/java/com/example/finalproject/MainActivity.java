package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread fadeIn=new Thread(){
            public void run (){
                try {
                    sleep(2000);
                    Intent login=new Intent(getApplicationContext(), Login.class);
                    startActivity(login);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };
        fadeIn.start();
    }
}