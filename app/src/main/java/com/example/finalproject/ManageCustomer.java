package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ManageCustomer extends AppCompatActivity {
    EditText customerUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_customer);
        customerUsername = findViewById(R.id.customerUsername);
    }

    public void goToUpdate(View view) {
        String userName = customerUsername.getText().toString().trim();
        String url = "http://192.168.1.3/android/getCustomers.php";
        RequestQueue queue = Volley.newRequestQueue(ManageCustomer.this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, response -> {

            ArrayList<JSONObject> users = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    users.add(response.getJSONObject(i));
                } catch (JSONException exception) {
                    Log.d("Error", exception.toString());
                }
            }

            if (!userName.isEmpty()) {
                for (int i = 0; i < users.size(); i++) {
                    try {
                        if (userName.equals(users.get(i).getString("Username"))) {

                            Intent intent = new Intent(getApplicationContext(), UpdateCustomer.class);
                            intent.putExtra("customerName", customerUsername.getText().toString().trim());
                            intent.putExtra("fName", users.get(i).getString("FirstName"));
                            intent.putExtra("lName", users.get(i).getString("LastName"));
                            startActivity(intent);
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(this, "Wrong Password or Username.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please fill in the data.",
                        Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(this, error.toString(),
                Toast.LENGTH_SHORT).show());
        queue.add(request);
    }

    public void deleteCustomer(View view) {
        String custuser = customerUsername.getText().toString().trim();
        delete(custuser);
        Intent intent = new Intent(getApplicationContext(), HomeReceptionist.class);
        startActivity(intent);

    }

    public void delete(String username) {
        String url = "http://10.0.2.2/android/removeCustomer.php";
        RequestQueue queue = Volley.newRequestQueue(ManageCustomer.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            Log.e("TAG", "RESPONSE IS " + response);

            Context context = getApplicationContext();
            CharSequence text = "Customer was deleted!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();


        }, error -> {
            // method to handle errors.
            Toast.makeText(ManageCustomer.this,
                    "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
        }) {
            @Override
            public String getBodyContentType() {

                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("username", username);

                return params;
            }
        };

        queue.add(request);
    }

    public void back1(View view) {
        Intent intent = new Intent(getApplicationContext(), HomeReceptionist.class);
        startActivity(intent);
    }
}