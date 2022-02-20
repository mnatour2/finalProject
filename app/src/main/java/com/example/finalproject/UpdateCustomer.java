package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UpdateCustomer extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    String customerName;
    String fName;
    String lName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customer);
        firstName = findViewById(R.id.updateFN);
        lastName = findViewById(R.id.updateLN);
        customerName = getIntent().getStringExtra("customerName");
        fName = getIntent().getStringExtra("fName");
        lName = getIntent().getStringExtra("lName");
        firstName.setText(fName);
        lastName.setText(lName);
        firstName = findViewById(R.id.updateFN);
        lastName = findViewById(R.id.updateLN);
    }

    public void updateCust(View view) {
        String fName = firstName.getText().toString().trim();
        String lName = lastName.getText().toString().trim();
        update(customerName, fName, lName);

        Intent intent = new Intent(getApplicationContext(), ManageCustomer.class);
        startActivity(intent);
    }

    private void update(String customerName, String fName, String lName) {
        String url = "http://10.0.2.2/android/updateCustomer.php";
        RequestQueue queue = Volley.newRequestQueue(UpdateCustomer.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            Log.e("TAG", "RESPONSE IS " + response);

            Context context = getApplicationContext();
            CharSequence text = "Customer was updated!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();


        }, error -> {
            // method to handle errors.
            Toast.makeText(UpdateCustomer.this,
                    "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
        }) {
            @Override
            public String getBodyContentType() {

                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("customer_name", customerName);
                params.put("first_name", fName);
                params.put("last_name", lName);

                return params;
            }
        };

        queue.add(request);
    }

    public void back(View view) {
        Intent intent = new Intent(getApplicationContext(), ManageCustomer.class);
        startActivity(intent);
    }
}