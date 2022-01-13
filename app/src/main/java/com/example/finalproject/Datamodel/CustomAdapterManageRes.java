package com.example.finalproject.Datamodel;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;
import com.example.finalproject.ReserveRoom;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

public class CustomAdapterManageRes extends BaseAdapter {
    Context context;
    String currentWorkingUser;
    String countryList[];
    int flags[];
    LayoutInflater inflter;

    public CustomAdapterManageRes(Context applicationContext, String[] countryList, int[] flags) {
        this.context = applicationContext;
        this.countryList = countryList;
        this.flags = flags;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void setNewData(int[] flags, String[] countryList) {
        this.flags = flags;
        this.countryList = countryList;
    }

    @Override
    public int getCount() {
        return countryList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.listviewmngresadapter, null);
        TextView country = (TextView) view.findViewById(R.id.mngresview);
        ImageView icon = (ImageView) view.findViewById(R.id.mngresicon);
        Button btn_checkin = (Button) view.findViewById(R.id.button_checkin);
        Button btn_unres = (Button) view.findViewById(R.id.button_unreserve);
        country.setText(countryList[i]);
        icon.setImageResource(flags[i]);

        btn_checkin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }

        });

        btn_unres.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                removeReservations(i);
                //viewGroup.setVisibility(View.INVISIBLE);
                icon.setImageResource(R.drawable.nores);
                notifyDataSetChanged();
            }

        });


        return view;
    }
    private void loadUser(Context cxt){
        SharedPreferences prefs;
        prefs = PreferenceManager.getDefaultSharedPreferences(cxt);
        String jsonuname = prefs.getString("username", "");
        Gson gson = new Gson();
        currentWorkingUser = gson.fromJson(jsonuname, String.class);
        Log.d("current user:", currentWorkingUser);
    }

    private void removeReservations(int roomid){   //removes old reservations from database after dates pass
        String url = "http://192.168.0.102/android/removeReservation.php";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(context,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(context,
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
}