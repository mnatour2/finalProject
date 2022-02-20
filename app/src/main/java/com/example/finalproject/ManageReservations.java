package com.example.finalproject;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.Datamodel.CustomAdapterManageRes;
import com.example.finalproject.Datamodel.ReservationModel;
import com.example.finalproject.Datamodel.VolleyCallBack;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManageReservations extends Activity {
    String currentWorkingUser;
    ListView simpleList;
    CustomAdapterManageRes customAdapter;
    private ArrayList<ReservationModel> dataSet;
    private static final String url = "http://192.168.1.3/android/getReservations.php";
    private RequestQueue queue;
    String[] rooms = { "Luxury room one", "Presidential suite",
            "kingpin room", "Luxury room two",
            "comfort family room"};
    String[] descriptions;
    int[] images = {R.drawable.room1,R.drawable.room2,R.drawable.room3,R.drawable.room4,R.drawable.room5};
    ArrayList<String> desc;
    ArrayList<Integer> room;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_reservations);
        descriptions = getResources().getStringArray(R.array.room_desc);
        queue = Volley.newRequestQueue(this);
        simpleList = (ListView) findViewById(R.id.simpleListView);
        loadUser(simpleList.getContext());
        dataSet = new ArrayList<>();
        desc = new ArrayList<>();
        room = new ArrayList<>();
        Thread getdata = new Thread(new Runnable() {
            @Override
            public void run() {
                loadReservations(new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        Log.d("volleyCB", "volley done");
                        int[] arr = room.stream().mapToInt(i -> i).toArray();
                        String[] str = new String[desc.size()];

                        for (int i = 0; i < desc.size(); i++) {
                            str[i] = desc.get(i);
                        }
                        customAdapter.setNewData(arr, str);
                        customAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        getdata.start();
        //loadReservations();
        customAdapter = new CustomAdapterManageRes(getApplicationContext(), descriptions, images);
        simpleList.setAdapter(customAdapter);

        //test code that works, build on this to change data depending on user decisions

        //images = new int[]{R.drawable.room1, R.drawable.room2};

    }

    private void loadUser(Context cxt){
        SharedPreferences prefs;
        prefs = PreferenceManager.getDefaultSharedPreferences(cxt);
        String jsonuname = prefs.getString("username", "");
        Gson gson = new Gson();
        currentWorkingUser = gson.fromJson(jsonuname, String.class);
        Log.d("current user:", currentWorkingUser);
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
                                Log.d("user data: ", String.valueOf(room_id));
                                dataSet.add(new ReservationModel(uname, endDate, room_id));
                                if(currentWorkingUser.equals(uname)) {
                                    Log.d("data read from user: ", String.valueOf(room_id));
                                    room.add(images[room_id]);
                                    desc.add(descriptions[room_id]);
                                    volleyCallBack.onSuccess();
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ManageReservations.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(ManageReservations.this).add(stringRequest);
    }


}