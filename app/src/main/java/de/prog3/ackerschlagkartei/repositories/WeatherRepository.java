package de.prog3.ackerschlagkartei.repositories;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.firestore.GeoPoint;

import org.json.JSONException;
import org.json.JSONObject;

import de.prog3.ackerschlagkartei.models.WeatherModel;

public class WeatherRepository {
    private final Application application;
    private final String apiUrl = "api.openweathermap.org/data/2.5/weather?appid=ed185425a6c757c3f10bc6904baa1341";

    private final MutableLiveData<WeatherModel> weatherModelMutableLiveData;

    public WeatherRepository(Application application) {
        this.application = application;
        this.weatherModelMutableLiveData = new MutableLiveData<>();
    }

    public void loadWeather(GeoPoint geoPoint) {

        String url = apiUrl;
        url += "?lat=" + geoPoint.getLatitude();
        url += "?lon=" + geoPoint.getLongitude();
        url += "?units=metric";

        new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String name = response.getString("name");
                    double temp = response.getJSONObject("main").getDouble("temp");

                    weatherModelMutableLiveData.postValue(new WeatherModel(name, temp));

                } catch (JSONException e) {
                    Toast.makeText(application, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError e) {
                Toast.makeText(application, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public MutableLiveData<WeatherModel> getWeatherModelMutableLiveData() {
        return weatherModelMutableLiveData;
    }
}
