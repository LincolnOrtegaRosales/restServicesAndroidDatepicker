package com.upc.semana5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AvisosBuscarActivity extends AppCompatActivity {

    DatePickerDialog picker;
    EditText eText;
    Button btnGet;
    TextView tvw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos_buscar);

        tvw=(TextView)findViewById(R.id.textView1);
        eText=(EditText) findViewById(R.id.editText1);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AvisosBuscarActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
//        btnGet=(Button)findViewById(R.id.button1);
//        btnGet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tvw.setText("Selected Date: "+ eText.getText());
//            }
//        });
    }


    public void buscar(View v){
//        EditText txtCriterio = findViewById(R.id.txtCriterio);
//        String criterio = txtCriterio.getText().toString();
        String url = "http://condeleron.atwebpages.com/index.php/avisos/"+eText.getText();

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                Log.i("======>", jsonArray.toString());

                List<String> items = new ArrayList<>();
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    items.add(object.getString("titulo") + " (Fecha Inicio: "+object.getString("fecha_inicio")+", Fecha Fin: "+object.getString("fecha_fin")+") ");
                }

                ListView lstAvisos = findViewById(R.id.listaAvisos);
                ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                        AvisosBuscarActivity.this,
                        android.R.layout.simple_list_item_1,
                        items);
                lstAvisos.setAdapter(adaptador);

            } catch (JSONException e) {
                Log.i("======>", e.getMessage());
            }
        },
                error -> Log.i("======>", error.toString())
        );
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void registrarAvisos(View view) {
        Intent intent = new Intent(this, AvisosRegistrarActivity.class);
        startActivity(intent);
    }
}
