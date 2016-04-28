package weather.com.weathertoday.utilities;

/**
 * Created by MMT3760 on 3/12/2016.
 */
public class RequestGenerator {


    public String getData(int requestType, String url, WeatherCallback callback) {


        new NetworkDataHandler(callback).execute(url);

        return null;
    }

}
