package com.atikah.gisrumahsakit;

import androidx.appcompat.app.AppCompatActivity;

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

public class InputdataActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private TextView txtdata;

    private EditText Namarumahsakit, Alamat, Notelpon, Lokasi;
    private Button btnsimpan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputdata);
        mRequestQueue = Volley.newRequestQueue(this);

        txtdata = (TextView) findViewById(R.id.txtdataku);

        Namarumahsakit = (EditText) findViewById(R.id.edtnamarumahsakit);
        Alamat =(EditText) findViewById(R.id.edtalamat);
        Notelpon = (EditText) findViewById(R.id.edtnotelpon);
        Lokasi = (EditText) findViewById(R.id.edtlokasi);

        btnsimpan = (Button) findViewById(R.id.btnsimpan);

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strnamarumahsakit = Namarumahsakit.getText().toString();
                String stralamat = Alamat.getText().toString();
                String strnotelpon = Notelpon.getText().toString();
                String strlokasi = Lokasi.getText().toString();

                if(strnamarumahsakit.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Namarumahsakit tidak boleh kosong",
                            Toast.LENGTH_LONG).show();
                } else if(stralamat.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Alamat tidak boleh kosong",
                            Toast.LENGTH_LONG).show();
                } else  if(strnotelpon.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Notelpon tidak boleh kosong",
                            Toast.LENGTH_LONG).show();
                } else  if(strlokasi.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Lokasi tidak boleh kosong",
                            Toast.LENGTH_LONG).show();
                } else {
                    inputData(strnamarumahsakit, stralamat, strnotelpon, strlokasi);

                    Intent a = new Intent(InputdataActivity.this,MainActivity.class);
                    startActivity(a);
                    finish();
                    Toast.makeText(getApplicationContext(), "Input Data Tersimpan",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        fetchJsonResponse();
    }
    private void fetchJsonResponse() {
        // Pass second argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, ConfigUrl.getAllrumahsakit, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String result = response.getString("data");
                            Toast.makeText(InputdataActivity.this, result, Toast.LENGTH_SHORT).show();
                            JSONArray res = new JSONArray(result);
                            for ( int i = 0; i < res.length(); i++){
                                JSONObject jObj = res.getJSONObject(i);
                                txtdata.append("namarumahsakit = "+ jObj.getString("namarumahsakit") + "\n"+
                                        "alamat = " + jObj.getString("alamat") + "\n" +
                                        "notelpon = " + jObj.getString("notelpon") + "\n" +
                                        "lokasi = " + jObj.getString("lokasi") + "\n\n");
                                Log.v("ini data dari server",result.toString());
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }



    private void inputData(String namarumahsakit, String alamat, String notelpon, String lokasi){
// Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("namarumahsakit", namarumahsakit);
        params.put("alamat", alamat);
        params.put("Notelpon", notelpon);
        params.put("Lokasi", lokasi);

        JsonObjectRequest req = new JsonObjectRequest(ConfigUrl.InputDatarumahsakit, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

// add the request object to the queue to be executed
        //ApplicationController.getInstance().addToRequestQueue(req);
        mRequestQueue.add(req);
    }

}