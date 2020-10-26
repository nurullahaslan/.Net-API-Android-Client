package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Visibility;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    TextView txtList;
    EditText txtName;
    EditText txtSurname;
    Button btnGet;
    Button btnPost;
    Button btnSave;
    Button btnRead;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtList=findViewById(R.id.txtList);
        txtName=findViewById(R.id.txtName);
        txtSurname=findViewById(R.id.txtSurname);
        btnGet=findViewById(R.id.btnGet);
        btnPost=findViewById(R.id.btnPost);
        btnSave=findViewById(R.id.btnSave);
        btnRead=findViewById(R.id.btnRead);

        txtList.setVisibility(View.GONE);
        txtName.setVisibility(View.GONE);
        txtSurname.setVisibility(View.GONE);
        btnGet.setVisibility(View.GONE);
        btnPost.setVisibility(View.GONE);
        url="https://4e6501fdcc07.ngrok.io/api/People/";


        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {txtList.setVisibility(View.GONE);
                txtList.setVisibility(View.GONE);
                txtName.setVisibility(View.VISIBLE);
                txtSurname.setVisibility(View.VISIBLE);
                btnGet.setVisibility(View.GONE);
                btnPost.setVisibility(View.VISIBLE);


            }

        });
        btnRead.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                txtList.setVisibility(View.VISIBLE);
                txtName.setVisibility(View.GONE);
                txtSurname.setVisibility(View.GONE);
                btnGet.setVisibility(View.VISIBLE);
                btnPost.setVisibility(View.GONE);


            }

        });
        btnPost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("name", txtName.getText());
                    jsonBody.put("surname", txtSurname.getText());
                    final String requestBody = jsonBody.toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("test", response);
                            Toast toast = Toast.makeText(getApplicationContext(), "Veritabanına başarıyla kaydedildi.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("test", error.toString());
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return requestBody == null ? null : requestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {
                                responseString = String.valueOf(response.statusCode);
                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(stringRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        });
        btnGet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jarr = null;
                        try {
                            jarr=new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JSONObject json1 = null;
                        for (int arr = 0; arr < jarr.length(); arr++) {

                            try {
                                json1 = jarr.getJSONObject(arr);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        String name = null;
                        String surname = null;
                        try {
                             name=json1.getString("name");
                             surname=json1.getString("surname");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        txtList.setText("Name : "+name+"\nSurname : " + surname);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) ;
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(stringRequest);

            }

        });
    }
}