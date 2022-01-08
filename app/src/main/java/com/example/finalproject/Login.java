package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private RequestQueue queue;
    boolean valid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = findViewById(R.id.usernameTxt);
        password = findViewById(R.id.passwordTxt);
        queue = Volley.newRequestQueue(this);
    }

    public void onLogin(View view) {

        String enteredUsername = username.getText().toString();
        String enteredPassword = password.getText().toString();

        if (enteredPassword.contains("admin") && enteredUsername.contains("admin")) {
            Intent homeReceptionist = new Intent(getApplicationContext(), HomeReceptionist.class);
            startActivity(homeReceptionist);
        } else {

            isValid(enteredUsername, enteredPassword);
            if (valid) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = prefs.edit();
                Gson gson = new Gson();
                String gsonName = gson.toJson(enteredUsername);
                editor.putString("username", gsonName);
                editor.commit();

                Intent homeCustomer = new Intent(getApplicationContext(), HomeCustomer.class);
                startActivity(homeCustomer);
            }
        }

    }

    public void isValid(String username, String password) {

        String url = "http://192.168.1.138/android/getCustomers.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> userNames = new ArrayList<>();
                ArrayList<String> passwords = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        userNames.add(obj.getString("Username"));
                        passwords.add(obj.getString("Pass"));
                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }

                for (int i = 0; i < userNames.size(); i++) {
                    if ((userNames.get(i).contains(username)) && (passwords.get(i).contains(password))) {
                        valid = true;
                        return;
                    }
                }
                Toast.makeText(Login.this, "No such customer, Please register.",
                        Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Login.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    public void onRegister(View view) {
        Intent register = new Intent(getApplicationContext(), Register.class);
        startActivity(register);
    }
}