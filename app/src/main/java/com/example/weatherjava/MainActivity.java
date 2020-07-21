package com.example.weatherjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView tv_address;
    TextView tv_temp;
    TextView tv_status;
    TextView tv_date;
    TextView temp_min;
    TextView temp_max;
    TextView humidity;
    TextView pressure;
    TextView mWind;

//    private final String CITY = "Bandung";
//    private final String  API = "6b14ba1c7c6c9960d4c4f54edb8d8773";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.loader);
        tv_address = findViewById(R.id.address);
        tv_temp = findViewById(R.id.temp);
        tv_status = findViewById(R.id.status);
        tv_date = findViewById(R.id.updated_at);
        temp_min = findViewById(R.id.temp_min);
        temp_max = findViewById(R.id.temp_max);
        humidity = findViewById(R.id.humidity);
        pressure = findViewById(R.id.pressure);
        mWind = findViewById(R.id.wind);

        getData();
    }

    private void getData() {
   //     RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        String url = "https://api.openweathermap.org/data/2.5/weather?q=Bandung&units=metric&appid=6b14ba1c7c6c9960d4c4f54edb8d8773";

  //      StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);

                try {
                    JSONObject main = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    JSONObject wind = response.getJSONObject("wind");

                    String desc = object.getString("description");
                    String city = response.getString("name");
                    String temp = main.getString("temp") + "°C";
                    String min = "Min Temp: " + main.getString("temp_min");
                    String max = "Max temp: " + main.getString("temp_max");
                    String hum = main.getString("humidity");
                    String pres = main.getString("pressure");
                    String windString = wind.getString("speed");

                    tv_address.setText(city);
                    tv_temp.setText(temp);
                    tv_status.setText(desc);
                    temp_min.setText(min);
                    temp_max.setText(max);
                    humidity.setText(hum);
                    pressure.setText(pres);
                    mWind.setText(windString);

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.US);
                    String formatted_date = "Update at: " + sdf.format(calendar.getTime());

                    tv_date.setText(formatted_date);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility((View.GONE));
                Log.d("Error Response", error.toString());
            }


    });

    RequestQueue queue = Volley.newRequestQueue(this);
    queue.add(jor);


    }
}
