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

import com.example.finalproject.R;
import com.google.gson.Gson;

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

                //Here perform the action you want
            }

        });

        btn_unres.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Here perform the action you want
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
}