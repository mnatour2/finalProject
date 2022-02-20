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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private RequestQueue queue;

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
        }
    }

    public void isValid(String username, String password) {
        String url = "http://192.168.1.3/android/getCustomers.php";
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

            if (!password.isEmpty() && !username.isEmpty()) {
                for (int i = 0; i < users.size(); i++) {
                    try {
                        if (username.equals(users.get(i).getString("Username")) && password.equals(users.get(i).getString("Pass"))) {
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                            SharedPreferences.Editor editor = prefs.edit();
                            Gson gson = new Gson();
                            String gsonName = gson.toJson(username);
                            editor.putString("username", gsonName);
                            editor.commit();
                            System.out.println(gsonName);
                            Intent homeCustomer = new Intent(getApplicationContext(), HomeCustomer.class);
                            startActivity(homeCustomer);
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(Login.this, "Wrong Password or Username.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Login.this, "Please fill in the data.",
                        Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(Login.this, error.toString(),
                Toast.LENGTH_SHORT).show());
        queue.add(request);
    }

    public void onRegister(View view) {
        Intent register = new Intent(getApplicationContext(), Register.class);
        startActivity(register);
    }
}