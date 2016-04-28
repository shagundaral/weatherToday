package weather.com.weathertoday.utilities;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import static android.content.Context.LOCATION_SERVICE;
import static android.location.Criteria.ACCURACY_FINE;
import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;
import static android.location.LocationManager.PASSIVE_PROVIDER;

/**
 * Created by MMT3760 on 3/12/2016.
 */
public class WeatherUtil {

    Context mContext;

    public WeatherUtil(Context context){
        this.mContext = context;
    }

    /**
     * Get the latest location trying multiple providers
     * <p>
     * Calling this method requires that your application's manifest contains the
     * {@link android.Manifest.permission#ACCESS_FINE_LOCATION} permission
     *
     * @return latest location set or null if none
     */
    public Location getLatestLocation() {
        try{
            LocationManager manager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            criteria.setAccuracy(ACCURACY_FINE);
            String provider = manager.getBestProvider(criteria, true);
            Location bestLocation;
            if (provider != null)
                bestLocation = manager.getLastKnownLocation(provider);
            else
                bestLocation = null;
            Location latestLocation = getLatest(bestLocation,manager.getLastKnownLocation(GPS_PROVIDER));
            latestLocation = getLatest(latestLocation,manager.getLastKnownLocation(NETWORK_PROVIDER));
            latestLocation = getLatest(latestLocation, manager.getLastKnownLocation(PASSIVE_PROVIDER));
            return latestLocation;
        }catch (Throwable e){

        }
        return null;
    }

    /**
     * Get the location with the later date
     *
     * @param location1
     * @param location2
     * @return location
     */
    private Location getLatest(final Location location1,
                               final Location location2) {
        if (location1 == null) {
            return location2;
        }
        if (location2 == null) {
            return location1;
        }
        if (location2.getTime() > location1.getTime()) {
            return location2;
        }else {
            return location1;
        }
    }


    /**
     *
     * @param callback
     */
    public static void mockResponse(WeatherCallback callback) {
        String sampleResponse = "{\n" +
                "\t\"coord\" : {\n" +
                "\t\t\"lon\" : 138.93,\n" +
                "\t\t\"lat\" : 34.97\n" +
                "\t},\n" +
                "\t\"weather\" : [{\n" +
                "\t\t\t\"id\" : 800,\n" +
                "\t\t\t\"main\" : \"Clear\",\n" +
                "\t\t\t\"description\" : \"clear sky\",\n" +
                "\t\t\t\"icon\" : \"01n\"\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"base\" : \"stations\",\n" +
                "\t\"main\" : {\n" +
                "\t\t\"temp\" : 283.179,\n" +
                "\t\t\"pressure\" : 1027.37,\n" +
                "\t\t\"humidity\" : 100,\n" +
                "\t\t\"temp_min\" : 283.179,\n" +
                "\t\t\"temp_max\" : 283.179,\n" +
                "\t\t\"sea_level\" : 1037.18,\n" +
                "\t\t\"grnd_level\" : 1027.37\n" +
                "\t},\n" +
                "\t\"wind\" : {\n" +
                "\t\t\"speed\" : 3.07,\n" +
                "\t\t\"deg\" : 252.501\n" +
                "\t},\n" +
                "\t\"clouds\" : {\n" +
                "\t\t\"all\" : 0\n" +
                "\t},\n" +
                "\t\"dt\" : 1457780057,\n" +
                "\t\"sys\" : {\n" +
                "\t\t\"message\" : 0.0115,\n" +
                "\t\t\"country\" : \"JP\",\n" +
                "\t\t\"sunrise\" : 1457729913,\n" +
                "\t\t\"sunset\" : 1457772583\n" +
                "\t},\n" +
                "\t\"id\" : 1851632,\n" +
                "\t\"name\" : \"Shuzenji\",\n" +
                "\t\"cod\" : 200\n" +
                "}";
        callback.onResponse(sampleResponse);
    }

}
