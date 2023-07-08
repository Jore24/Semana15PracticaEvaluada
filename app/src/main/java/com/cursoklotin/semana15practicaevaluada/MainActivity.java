package com.cursoklotin.semana15practicaevaluada;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView txtId, txtUserId, txtTitle, txtBody;
    Button btnGet, btnPost, btnActualizar, btnLimpiar;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtId = findViewById(R.id.tvId);
        txtUserId = findViewById(R.id.tvUserId);
        txtTitle = findViewById(R.id.tvTitle);
        txtBody = findViewById(R.id.tvBody);
        btnGet = findViewById(R.id.btn_get);
        btnPost = findViewById(R.id.btn_post);
        btnActualizar = findViewById(R.id.btn_actualizar);
        btnLimpiar = findViewById(R.id.btn_limpiar);


        requestQueue = Volley.newRequestQueue(this);
        btnGet.setOnClickListener(v -> {
            String id = txtId.getText().toString(); // Obtener el ID del txtId
            LeerWs(id);
        });

        btnPost.setOnClickListener(v -> {
            String title = txtTitle.getText().toString();
            String body = txtBody.getText().toString();
            String userId = txtUserId.getText().toString();
            enviarWs(title, body, userId);
        });

        btnActualizar.setOnClickListener(v -> {
            String id = txtId.getText().toString();
            String title = txtTitle.getText().toString();
            String body = txtBody.getText().toString();
            String userId = txtUserId.getText().toString();
            actualizarWs(id, title, body, userId);
        });

        btnLimpiar.setOnClickListener(v -> {
            txtId.setText("");
            txtUserId.setText("");
            txtTitle.setText("");
            txtBody.setText("");
        });


    }

    public void LeerWs(String id) {
        String url = "https://jsonplaceholder.typicode.com/posts/" + id;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txtId.setText(jsonObject.getString("id"));
                    txtUserId.setText(jsonObject.getString("userId"));
                    txtTitle.setText(jsonObject.getString("title"));
                    txtBody.setText(jsonObject.getString("body"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(request);
    }

    public void enviarWs(String title, String body, String userId) {
        String url = "https://jsonplaceholder.typicode.com/posts";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Datos enviados: " + response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error al enviar datos", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("body", body);
                params.put("userId", userId);
                return params;
            }
        };

        requestQueue.add(request);
    }

    public void actualizarWs(String id, String title, String body, String userId) {
        String url = "https://jsonplaceholder.typicode.com/posts/" + id;
        StringRequest request = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Datos actualizados: " + response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error al actualizar datos", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("title", title);
                params.put("body", body);
                params.put("userId", userId);
                return params;
            }
        };

        requestQueue.add(request);
    }
}
