package com.atikah.gisrumahsakit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import server.ConfigUrl;


public class RegistrasiActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;

    private EditText edtuser,edtnotelpon,edtPassword;
    private Button btnregis;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        mRequestQueue = Volley.newRequestQueue(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        edtuser = findViewById(R.id.edtuser);
        edtnotelpon = findViewById(R.id.edtnotelpon);
        edtPassword = findViewById(R.id.edtPassword);

        btnregis = findViewById(R.id.btnkirim);

        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String struser = edtuser.getText().toString();
                String strnotelpon = edtnotelpon.getText().toString();
                String strPassword = edtPassword.getText().toString();

                if (struser.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "User Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else if (strnotelpon.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Nomor telpon Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else if (strPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else {
                    InputData(struser, strnotelpon, strPassword);

//                    Intent a = new Intent(RegistrasiActivity.this, LoginActivity.class);
//                    startActivity(a);
//                    finish();
                }
            }
        });
    }

    private void InputData(String user, String notelpon, String pass){

// Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("User", user);
        params.put("Notelpon", notelpon);
        params.put("Password", pass);

        pDialog.setMessage("Mohon Tunggu");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(ConfigUrl.inputDatarumahsakit, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            String msg;
                            if(status == true){
                                msg = response.getString("pesan");
                            } else {
                                msg = response.getString("pesan");
                                Intent a = new Intent(RegistrasiActivity.this, LoginActivity.class);
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
        if (!pDialog.isShowing())
            pDialog.dismiss();
    }
}