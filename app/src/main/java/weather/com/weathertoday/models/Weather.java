package weather.com.weathertoday.models;

/**
 * Created by MMT3760 on 3/12/2016.
 */
public class Weather {

    public Integer id;
    public String main;
    public String description;
    public String icon;

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", main='" + main + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
