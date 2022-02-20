package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddRoom extends AppCompatActivity {

    EditText bed, baths, description, balcony;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_rooms);

        bed = findViewById(R.id.bed);
        baths = findViewById(R.id.baths);
        description = findViewById(R.id.description);
        balcony = findViewById(R.id.balcony);


    }


    public void addRoom(View view) {
        String numOfbed = bed.getText().toString();
        String numOfBath = baths.getText().toString();
        String des = description.getText().toString();
        String bal = balcony.getText().toString();
        added(numOfbed, numOfBath, des, bal);
        Intent homeReceptionist = new Intent(getApplicationContext(), HomeReceptionist.class);
        startActivity(homeReceptionist);
    }

    public void added(String numOfbed,String numOfBath,String des,String bal){
        String url = "http://10.0.2.2/android/addroom.php";
        RequestQueue queue = Volley.newRequestQueue(AddRoom.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            Log.e("TAG", "RESPONSE IS " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                // on below line we are displaying a success toast message.
                Context context = getApplicationContext();
                CharSequence text = "Room was added!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            // method to handle errors.
            Toast.makeText(AddRoom.this,
                    "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
        }) {
            @Override
            public String getBodyContentType() {

                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("numberOfbed", numOfbed);
                params.put("numberOfBathrooms", numOfBath);
                params.put("description", des);
                params.put("hasbalcony", bal);

                return params;
            }
        };

        queue.add(request);

    }

    public void back2(View view) {
        Intent home=new Intent(getApplicationContext(), HomeReceptionist.class);
        startActivity(home);
    }
}










