package weather.com.weathertoday;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import weather.com.weathertoday.models.WeatherDataByLatLong;
import weather.com.weathertoday.utilities.NetworkDataHandler;
import weather.com.weathertoday.utilities.StringUtils;
import weather.com.weathertoday.utilities.WeatherCallback;
import weather.com.weathertoday.utilities.WeatherConstants;
import weather.com.weathertoday.utilities.WeatherUtil;

/**
 * Created by MMT3760 on 3/12/2016.
 */
public class WeatherActivity extends AppCompatActivity implements WeatherCallback {

    AutoCompleteTextView editCity;
    List<String> cityList = new ArrayList<String>();
    TextView cityName, temparature, temparatureUnit, weatherDesc, min_temp, max_temp;
    WeatherCallback mCallback;
    RecyclerView weatherCards, forecastList;
    RelativeLayout pageMain;
    boolean isUnitDegree = true;
    ImageView picOfTheDay;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_page_layout);
        mCallback = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        getDataForCityList();
        initializeUI();
        initialize();
    }

    private void initialize() {

        Intent intent = getIntent();
        String action = intent.getAction();
        Log.e("check :: ",action);
        Uri data = intent.getData();
        String city = null;
        if(null!=data && !StringUtils.isNullOrEmpty(data.toString())){
            String[] uriSplit = data.toString().split("/");
            if(uriSplit.length>=3){
                city = uriSplit[uriSplit.length-1];
            }
            if(!StringUtils.isNullOrEmpty(city)) {
                getWeatherByCityName(city);
            }
        }else{
            getUserLocation();
        }
    }

    private void getWeatherByCityName(String city) {
        new NetworkDataHandler(mCallback).execute(String.format(WeatherConstants.weatherAPICity,city));
    }

    private void getWeatherByLatLong(Location location) {
        //send call
        if(null!=location) {
            new NetworkDataHandler(this).execute(String.format(WeatherConstants.weatherAPILatLong,location.getLatitude(),location.getLongitude()));
        }else{
            Toast.makeText( getApplicationContext(), "Please enable GPS", Toast.LENGTH_SHORT ).show();
            findViewById(R.id.weather_info_cards).setVisibility(View.GONE);
            findViewById(R.id.top_weather_card).setVisibility(View.GONE);
        }
//        initializeUI();
    }

    private void initializeUI() {
        cityName = (TextView) findViewById(R.id.location_name);
        weatherCards = (RecyclerView) findViewById(R.id.weather_info_cards);
        weatherCards.setNestedScrollingEnabled(true);
        weatherCards.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        min_temp = (TextView) findViewById(R.id.min_temp_text);
        max_temp = (TextView) findViewById(R.id.max_temp_text);
        temparature = (TextView) findViewById(R.id.location_temp);
        temparatureUnit = (TextView) findViewById(R.id.location_temp_unit);
        weatherDesc = (TextView) findViewById(R.id.location_weateher_desc);
        picOfTheDay = (ImageView) findViewById(R.id.pic_of_the_day);
        forecastList = (RecyclerView) findViewById(R.id.forecast_list);
        forecastList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        forecastList.setAdapter(new HorizontalForecastAdapter());

        editCity = (AutoCompleteTextView) findViewById(R.id.changeCityList);
        ArrayAdapter<String> cityListAdapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, cityList);
        editCity.setThreshold(1);
        editCity.setDropDownBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.bg_city));
        editCity.setAdapter(cityListAdapter);
        editCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                String city = ((android.support.v7.widget.AppCompatCheckedTextView) view).getText().toString();
                editCity.dismissDropDown();
                getWeatherByCityName(city);
            }
        });
        pageMain = (RelativeLayout) findViewById(R.id.page_main);
        /*
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        pageMain.setOnTouchListener(gestureListener);
        gestureDetector = new GestureDetector(new SwipeGestureDetector());*/
        picOfTheDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent picOfTheDay = new Intent(getApplicationContext(), PicOfDay.class);
                startActivity(picOfTheDay);
            }
        });

//        WeatherUtil.mockResponse(this);
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

    public void getUserLocation() {

        Location location = new WeatherUtil(this).getLatestLocation();
        getWeatherByLatLong(location);
    }

    @Override
    public void onResponse(String response) {

        Gson g = new Gson();
        WeatherDataByLatLong data = null;
        try {
            data = g.fromJson(response, WeatherDataByLatLong.class);
        } catch (Exception e) {
            Log.e("Response", "Mal formed response");
        }
        if (data == null) {
            Toast.makeText(WeatherActivity.this, "Error in response", Toast.LENGTH_SHORT).show();
            findViewById(R.id.weather_info_cards).setVisibility(View.VISIBLE);
            findViewById(R.id.top_weather_card).setVisibility(View.GONE);
            findViewById(R.id.forecast_text).setVisibility(View.GONE);
            findViewById(R.id.forecast_list).setVisibility(View.GONE);
            findViewById(R.id.weather_info_cards).setVisibility(View.GONE);
        } else {
            findViewById(R.id.weather_info_cards).setVisibility(View.VISIBLE);
            findViewById(R.id.top_weather_card).setVisibility(View.VISIBLE);
            findViewById(R.id.forecast_text).setVisibility(View.VISIBLE);
            findViewById(R.id.forecast_list).setVisibility(View.VISIBLE);
            findViewById(R.id.weather_info_cards).setVisibility(View.VISIBLE);
            initializeUI();
            handleResponse(data);
        }
    }

    private void handleResponse(WeatherDataByLatLong data) {
        weatherCards.setAdapter(new WeatherCardAdapter(getApplicationContext(), data));
        cityName.setText(data.name);
        editCity.setText(data.name);
        temparature.setText(String.valueOf((int)(data.main.temp-273.15)));
        temparatureUnit.setText("Degree");
        if(null!=data.main && null!=data.main.tempMax && null!=data.main.tempMin) {
            min_temp.setText(String.valueOf(data.main.tempMin));
            max_temp.setText(String.valueOf(data.main.tempMax));
        }

        temparature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUnitDegree) {
                    temparature.setText(String.valueOf((int) Double.parseDouble(temparature.getText().toString()) + 273));
                    temparatureUnit.setText("Kelvin");
                    isUnitDegree = false;
                } else {
                    temparature.setText(String.valueOf((int) Double.parseDouble(temparature.getText().toString()) - 273));
                    temparatureUnit.setText("Degree");
                    isUnitDegree = true;
                }
            }
        });

        weatherDesc.setText(data.weather.get(0).description);
        if(data.main.temp<295){
            pageMain.setBackgroundResource(R.drawable.bg_winter);
        }else{
            pageMain.setBackgroundResource(R.drawable.bg_haze);
        }
    }



    private class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_MIN_DISTANCE = 50;
        private static final int SWIPE_MAX_OFF_PATH = 200;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            try {
                Toast t = Toast.makeText(WeatherActivity.this, "Gesture detected", Toast.LENGTH_SHORT);
                t.show();
                float diffAbs = Math.abs(e1.getY() - e2.getY());
                float diff = e1.getX() - e2.getX();

                if (diffAbs > SWIPE_MAX_OFF_PATH)
                    return false;

                /*// Left swipe
                if (diff > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    WeatherActivity.this.onLeftSwipe();
                }*/
                // Right swipe
                else if (-diff > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    WeatherActivity.this.onRightSwipe();
                }
            } catch (Exception e) {
                Log.e("Home", "Error on gestures");
            }
            return false;
        }

    }

    private void onRightSwipe() {

        Intent i = new Intent(this, PicOfDay.class);
        startActivity(i);

    }

}
