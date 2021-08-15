package com.example.lattice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lattice.RetrofitWeather.models.WeatherData;
import com.example.lattice.RetrofitWeather.service.WeatherApiClient;
import com.example.lattice.RetrofitWeather.service.WeatherApiInterFace;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {

    private long backPressTime;

    private EditText mCityName;
    private Button mShowResult;
    private TextView mTempInC, mTempInF, mLongitude, mLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Today Weather");

        mCityName = findViewById(R.id.city_name);
        mShowResult = findViewById(R.id.show_result);

        mTempInC = findViewById(R.id.tempInC);
        mTempInF = findViewById(R.id.tempInF);

        mLatitude = findViewById(R.id.latitude);
        mLongitude = findViewById(R.id.longitude);

        mShowResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getWeatherData(mCityName.getText().toString().trim());
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (backPressTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        }
        backPressTime = System.currentTimeMillis();

    }

    private void getWeatherData(String name) {

        WeatherApiInterFace apiInterface = WeatherApiClient.getClient(WeatherActivity.this).create(WeatherApiInterFace.class);
        Call<WeatherData> call = apiInterface.getWeatherData(name);

        call.enqueue(new Callback<WeatherData>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {

                assert response.body() != null;
                mTempInC.setText(response.body().getCurrent().getTemp_c() + "°C");
                mTempInF.setText(response.body().getCurrent().getTemp_f() + "°F");

                mLatitude.setText(response.body().getLocation().getLat().toString());
                mLongitude.setText(response.body().getLocation().getLon().toString());
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {

            }
        });
    }
    }