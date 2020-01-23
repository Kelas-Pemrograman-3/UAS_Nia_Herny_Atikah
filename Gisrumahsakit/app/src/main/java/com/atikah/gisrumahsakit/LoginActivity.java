package com.atikah.gisrumahsakit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import server.ConfigUrl;
import session.sessionManager;

public class LoginActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;

    private EditText edtPassword, edtuser;
    private Button btnLinkRegister, btnlogin ;

    private ProgressDialog pDialog;

    private sessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);



        edtuser = findViewById(R.id.edtuser);
        edtPassword = findViewById(R.id.edtPassword);

        btnLinkRegister = findViewById(R.id.btnLinkRegister);

        btnLinkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegistrasiActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnlogin = findViewById(R.id.btnLogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String struser = edtuser.getText().toString();
                String strpass = edtPassword.getText().toString();

                if (struser.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"User tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (strpass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "password tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else {
                    login(struser, strpass);
                }
            }
        });

    }

    private void login(String user, String pass){

// Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("User", user);
        params.put("Password", pass);

        pDialog.setMessage("Mohon Tunggu");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(ConfigUrl.login, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            String msg;
                            if (status == true) {
                                msg = response.getString("pesan");
                            } else {
                                msg = response.getString("pesan");
                                Intent a = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(a);
                                finish();
                            }
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
//                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

// add the request object to the queue to be executed
// ApplicationController.getInstance().addToRequestQueue(req);
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}