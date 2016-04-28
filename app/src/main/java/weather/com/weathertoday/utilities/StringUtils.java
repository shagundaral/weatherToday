package weather.com.weathertoday.utilities;

/**
 * Created by MMT3760 on 3/12/2016.
 */
public class StringUtils {


    /**
     * Returns true if the given string is null or empty.
     */
    public static boolean isNullOrEmpty(final String str) {
        if (str == null) {
            return true;
        }
        if (str.trim().equals("")) {
            return true;
        }
        return false;
    }

}
