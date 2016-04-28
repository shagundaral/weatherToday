package weather.com.weathertoday.utilities;

/**
 * Created by MMT3760 on 3/12/2016.
 */
public class WeatherConstants {


    public final static String searchLocationAPI = "http://api.openweathermap.org/data/2.5/find?mode=json&type=like&q=%s&cnt=10&appid=b1b15e88fa797225412429c1c50c122a";
    public final static String weatherAPILatLong = "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=b1b15e88fa797225412429c1c50c122a";
    public final static String weatherAPICity = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=b1b15e88fa797225412429c1c50c122a";


}
