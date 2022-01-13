package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.AdapterView;
import android.view.View;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.Datamodel.ItemDataModel;
import com.example.finalproject.Datamodel.ReservationModel;
import com.example.finalproject.Datamodel.VolleyCallBack;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

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
    String[] descriptions;
    ImageView img;   //using position to get image
    TextView txt;
    String currentWorkingUser;
    int currentSelectedRoom;
    ReservationModel currentRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_room);
        loadUser(ReserveRoom.this);
        descriptions = getResources().getStringArray(R.array.room_desc);
        dataSet = new ArrayList<>();
        img = findViewById(R.id.reserve_room_view);
        txt = findViewById(R.id.room_desc_txt);
        currentSelectedRoom = 0;
        Thread getdata = new Thread(new Runnable() {
            @Override
            public void run() {
                loadReservations(new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        Log.d("volleyCB", "volley done");
                        clearOldReservations();
                    }
                });
            }
        });
        getdata.start();

        img.setImageResource(R.drawable.room1);
        txt.setText(descriptions[0]);
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
        currentSelectedRoom = i;
        switch (i) {
            case 0:
                img.setImageResource(R.drawable.room1);
                txt.setText(descriptions[i]);
                break;
            case 1:
                img.setImageResource(R.drawable.room2);
                txt.setText(descriptions[i]);
                break;
            case 2:
                img.setImageResource(R.drawable.room3);
                txt.setText(descriptions[i]);
                break;
            case 3:
                img.setImageResource(R.drawable.room4);
                txt.setText(descriptions[i]);
                break;
            case 4:
                img.setImageResource(R.drawable.room5);
                txt.setText(descriptions[i]);
                break;
            default:
                Log.d("error","couldnt detect selected item");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void onReserve(View view){
        int daylength = ((TextView)findViewById(R.id.textDay)).getText().toString().length();
        int monthlength = ((TextView)findViewById(R.id.textDay)).getText().toString().length();
        int yearlength = ((TextView)findViewById(R.id.textDay)).getText().toString().length();
        if(daylength==0 || monthlength ==0 || yearlength ==0){
            Toast.makeText(ReserveRoom.this,
                    "please enter the reservation final date"
                    , Toast.LENGTH_LONG).show();
            return;
        }
        if(checkReservation(currentSelectedRoom)){
            Toast.makeText(ReserveRoom.this,
                    "This room is currently reserved, try again in " + currentRoom.getEndDate()
                    , Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(ReserveRoom.this,
                    "This room has been reserved by you!"
                    , Toast.LENGTH_LONG).show();
            String date = ((TextView)findViewById(R.id.textDay)).getText().toString()+
                    "-"+((TextView)findViewById(R.id.textmonth)).getText().toString()+
                    "-"+((TextView)findViewById(R.id.textyear)).getText().toString();
            addToReservations(date, currentSelectedRoom, currentWorkingUser);
        }
    }

    public void onReturn(View view){
        Intent home=new Intent(getApplicationContext(), HomeCustomer.class);
        startActivity(home);
    }

    private void loadReservations(final VolleyCallBack volleyCallBack) {

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
                                volleyCallBack.onSuccess();
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

    private boolean checkReservation(int res){  //returns true if room id res is reserved
        ReservationModel model;
        for (int i = 0; i < dataSet.size(); i++) {
            model = dataSet.get(i);
            if(model.getRoom_id() == res) {
                currentRoom = model;
                return true;
            }
        }
        return false;
    }

    private void removeReservations(int roomid){   //removes old reservations from database after dates pass
        String url = "http://192.168.0.102/android/removeReservation.php";
        RequestQueue queue = Volley.newRequestQueue(ReserveRoom.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(ReserveRoom.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(ReserveRoom.this,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing
                // our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our
                // key and value pair to our parameters.
                params.put("room_id", String.valueOf(roomid));
                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void addToReservations(String date, int roomid, String uname){       //adds a reservation
        String url = "http://192.168.0.102/android/addReservation.php";
        RequestQueue queue = Volley.newRequestQueue(ReserveRoom.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(ReserveRoom.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(ReserveRoom.this,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing
                // our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our
                // key and value pair to our parameters.
                params.put("room_id", String.valueOf(roomid));
                params.put("uname", uname);
                params.put("endDate", date);

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void clearOldReservations(){
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH)+1;
        int year = c.get(Calendar.YEAR);
        //Log.d("checking date", String.valueOf(day)+String.valueOf(month)+String.valueOf(year));
        ReservationModel model;
        for (int i = 0; i < dataSet.size(); i++) {
            model = dataSet.get(i);
            String date = model.getEndDate();
            String[] result = date.split("-");
            int day2 = Integer.parseInt(result[0]);
            int month2 = Integer.parseInt(result[1]);
            int year2 = Integer.parseInt(result[2]);
            //Log.d("checking date:", date +"with"+ String.valueOf(day)+'-'+String.valueOf(month)+'-'+String.valueOf(year));
            if (year > year2 || (year == year2 && month > month2) ||
                    (year == year2 && month == month2 && day > day2 )){
                removeReservations(model.getRoom_id());
                dataSet.remove(i);
            }
        }
    }

    private void loadUser(Context cxt){
        SharedPreferences prefs;
        prefs = PreferenceManager.getDefaultSharedPreferences(cxt);
        String jsonuname = prefs.getString("username", "");
        Gson gson = new Gson();
        currentWorkingUser = gson.fromJson(jsonuname, String.class);
        Log.d("current user:", currentWorkingUser);
    }
}
