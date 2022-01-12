package com.example.finalproject.Datamodel;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.R;

import java.util.zip.Inflater;

public class CustomAdapterManageRes extends BaseAdapter {
    Context context;
    String countryList[];
    int flags[];
    LayoutInflater inflter;

    public CustomAdapterManageRes(Context applicationContext, String[] countryList, int[] flags) {
        this.context = context;
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
        country.setText(countryList[i]);
        icon.setImageResource(flags[i]);
        return view;
    }
}