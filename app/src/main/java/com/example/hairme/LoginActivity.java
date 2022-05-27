package com.example.hairme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hairme.Models.UserModle;
import com.example.hairme.Services.Mysingleton;
import com.example.hairme.Services.SharedPrefmanager;
import com.example.hairme.Services.URLs;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
EditText username ,password;
    AppCompatButton re;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username =findViewById(R.id.name_Email);
        password =findViewById(R.id.password);
        re =findViewById(R.id.rig);

        if(SharedPrefmanager.getInstance(getApplicationContext()).isLoggedIn()){
            startActivity(new Intent(getApplicationContext(),SandBox.class));
            finish();
        }
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish();
                startActivity( new Intent(LoginActivity.this,singupActivity.class));
            }
        });

        findViewById(R.id.let_user_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Objects.requireNonNull(username.getText()).toString().trim();
                String textpassword = Objects.requireNonNull(password.getText()).toString().trim();
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    username.setError("ادخل بريد صالح");
                    username.requestFocus();

                }else if (textpassword.length() == 0) {
                    password.requestFocus();
                    password.setError("عليك بملاء هذاالحقل");
                }else {
                    Login(email,textpassword);
                }

            }
        });
    }
    private void Login(String user, String passcode) {

        com.kaopiz.kprogresshud.KProgressHUD prog = com.kaopiz.kprogresshud.KProgressHUD.create(this)
                .setStyle(com.kaopiz.kprogresshud.KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("الرجاء الانتظار")
                .setDetailsLabel("يتم معالجة طلبك ")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setSize(200, 200)
                .show();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("Email", user);
        params.put("password", passcode);


        JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, URLs.URL_LOGIN, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                prog.dismiss();
                try {
                    if (response.getBoolean("error")) {
                        ///replace tosat with dilog
                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(getApplicationContext(), "Response:  " + response.getJSONObject("user"), Toast.LENGTH_SHORT).show();

                        JSONObject jsonArry = response.getJSONObject("user");
                        Gson gson = new Gson();
                        UserModle userModle = gson.fromJson(jsonArry.toString(), UserModle.class);
                        //storing the user in shared preferences
                        SharedPrefmanager.getInstance(getApplicationContext()).userLogin(userModle);
                        Intent intent = new Intent(LoginActivity.this, SandBox.class);
                        startActivity(intent);

                    }
                } catch (JSONException e) {
                    prog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prog.dismiss();
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(),
                            "ServerError!",
                            Toast.LENGTH_LONG).show();

                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(),
                            "AuthFailureError!",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(),
                            "ParseError!",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(),
                            "NoConnectionError!",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(),
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }

                Toast.makeText(getApplicationContext(), "Response:  " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                final Map<String, String> headers = new HashMap<>();
//                headers.put("Accept", "application/json" );//put your token here
//                    headers.put("Connection", "keep-alive" );//put your token here
                return headers;
            }
        };

        Mysingleton.getInstance(getApplicationContext()).addToReguestQueu(jsonOblect);
    }
}