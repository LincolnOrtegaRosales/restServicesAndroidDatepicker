package com.upc.semana5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ProductoRegistrarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_registrar);
    }

    public void registrar(View v){
        final EditText txtNombre = findViewById(R.id.txtNombre);
        final EditText txtPrecio = findViewById(R.id.txtPrecio);
        String url = "http://condeleron.atwebpages.com/index.php/productos";

        StringRequest stringRequest= new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast toast = Toast.makeText(ProductoRegistrarActivity.this,"Se insertó correctamente", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                },
                error -> Log.i("======>", error.toString())
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", txtNombre.getText().toString());
                params.put("precio", txtPrecio.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        /*
        JSONObject jsonobject = new JSONObject();
        try {
            jsonobject.put("idCategoria", "1");
            jsonobject.put("nombre", txtNombre.getText().toString());
            jsonobject.put("precio", txtDescripcion.getText().toString());
            Log.i("======>", jsonobject.toString());
        } catch (JSONException e) {
            Log.i("======>", e.getMessage());
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonobject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Success Callback
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("======>", error.getMessage());
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);
        */
        Intent intent = new Intent(this, ProductosBuscarActivity.class);
        startActivity(intent);
    }
}
