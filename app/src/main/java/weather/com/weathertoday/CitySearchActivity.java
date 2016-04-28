package weather.com.weathertoday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import weather.com.weathertoday.models.CityData;
import weather.com.weathertoday.utilities.CustomLinearLayoutManager;
import weather.com.weathertoday.utilities.NetworkDataHandler;
import weather.com.weathertoday.utilities.StringUtils;
import weather.com.weathertoday.utilities.WeatherCallback;
import weather.com.weathertoday.utilities.WeatherConstants;

/**
 * Created by MMT3760 on 3/12/2016.
 */
public class CitySearchActivity extends AppCompatActivity implements WeatherCallback{

    EditText citySearchText;
    WeatherCallback mCallback;
    Button getWeather;
    RecyclerView cityListView;
    String city = null;
    List<String> cityList = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_search_layout);
        init();
        initializeListeners();


        //below this is extra here
        String city = "Delhi";
        //citySearchText.getText().toString();

        if(!StringUtils.isNullOrEmpty(city)) {
            String urlString = String.format(WeatherConstants.searchLocationAPI, city);

            new NetworkDataHandler(mCallback).execute(urlString);
        }
    }

    private void init() {
        citySearchText = (EditText) findViewById(R.id.cityEdtText);
        getWeather = (Button) findViewById(R.id.get_weather_btn);
        cityListView = (RecyclerView) findViewById(R.id.cityList);
        getDataForCityList();
        if(null!=cityList && !cityList.isEmpty()){
            CityListAdapter cityAdapter = new CityListAdapter(cityList, getApplicationContext());
            cityListView.setAdapter(cityAdapter);
            cityListView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        }
    }

    private void initializeListeners() {
        mCallback = this;
        city = "Delhi";
        citySearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Toast.makeText(getApplicationContext(), "beforeTextChanged", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //do nothing
                Toast.makeText(getApplicationContext(), "onTextChanged", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
                Toast.makeText(getApplicationContext(), "afterTextChanged", Toast.LENGTH_SHORT).show();

            }
        });

        getWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CityWeatherTab.class);
                intent.putExtra("city", city);
                startActivity(intent);

            }
        });


    }

    @Override
    public void onResponse(String response) {
        Gson g = new Gson();
        CityData cityList = g.fromJson(response, CityData.class);
        Log.e("CitySearchActivity", cityList.toString());
    }


    public void getDataForCityList() {
        String cities = null;
        try {
            InputStream is = this.getResources().openRawResource(R.raw.cities);
            byte[] buffer = new byte[is.available()];
            while (is.read(buffer) != -1) ;
            cities = new String(buffer);
        }catch (IOException ioe){

        }
        cityList  = Arrays.asList(cities.split(","));
    }
}
