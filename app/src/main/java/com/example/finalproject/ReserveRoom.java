package com.example.finalproject;

import android.util.Log;
import android.widget.AdapterView;
import android.view.View;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.Datamodel.ItemDataModel;
import com.example.finalproject.Datamodel.ReservationModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// Main Activity implements Adapter view
public class ReserveRoom
        extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {
    private ArrayList<ReservationModel> dataSet;
    private static final String url = "http://192.168.0.102/android/getReservations.php";
    // create array of Strings
    // and store name of courses
    String[] rooms = { "Luxury room one", "Presidential suite",
            "kingpin room", "Luxury room two",
            "comfort family room"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_room);
        loadReservations();
        // Take the instance of Spinner and
        // apply OnItemSelectedListener on it which
        // tells which item of spinner is clicked
        Spinner spin = findViewById(R.id.spinner1);
        spin.setOnItemSelectedListener(this);

        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                rooms);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spin.setAdapter(ad);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ImageView img = findViewById(R.id.reserve_room_view);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void loadReservations() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String uname = object.getString("uname");
                                String endDate = object.getString("endDate");
                                int room_id = object.getInt("room_id");
                                Log.d("data read from user: ", uname);
                                dataSet.add(new ReservationModel(uname, endDate, room_id));
                            }
                        } catch (Exception e) {
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ReserveRoom.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(ReserveRoom.this).add(stringRequest);
    }
}
