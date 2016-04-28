package weather.com.weathertoday;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by MMT3760 on 3/12/2016.
 */
public class CityWeatherTab extends AppCompatActivity {

    String city;
    TextView cityName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_weather_tab);
        city = getIntent().getExtras().getString("city");
        init();
    }

    private void init() {
        cityName = (TextView) findViewById(R.id.city_name);
        cityName.setText(city);

    }
}
