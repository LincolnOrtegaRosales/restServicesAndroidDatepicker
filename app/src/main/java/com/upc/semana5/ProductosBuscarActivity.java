package com.upc.semana5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductosBuscarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_buscar);
    }

    public void buscar(View v){
        EditText txtCriterio = findViewById(R.id.txtCriterio);
        String criterio = txtCriterio.getText().toString();
        String url = "http://condeleron.atwebpages.com/index.php/productos/"+criterio;

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                Log.i("======>", jsonArray.toString());

                List<String> items = new ArrayList<>();
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    items.add(object.getString("nombre") + " (S/. "+object.getString("precio")+") ");
                }

                ListView lstProductos = findViewById(R.id.lista);
                ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                        ProductosBuscarActivity.this,
                        android.R.layout.simple_list_item_1,
                        items);
                lstProductos.setAdapter(adaptador);

            } catch (JSONException e) {
                Log.i("======>", e.getMessage());
            }
        },
                error -> Log.i("======>", error.toString())
        );
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
