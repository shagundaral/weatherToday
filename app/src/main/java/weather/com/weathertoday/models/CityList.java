package weather.com.weathertoday.models;

import java.util.ArrayList;

/**
 * Created by MMT3760 on 3/12/2016.
 */
public class CityList {

    public Integer id;
    public String name;
    public Coord coord;
    public WeatherData main;
    public Integer dt;
    public Wind wind;
    public Country sys;
    public Clouds clouds;
    public java.util.List<Weather> weather = new ArrayList<Weather>();

    @Override
    public String toString() {
        return "CityList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coord=" + coord +
                ", main=" + main +
                ", dt=" + dt +
                ", wind=" + wind +
                ", sys=" + sys +
                ", clouds=" + clouds +
                ", weather=" + weather +
                '}';
    }
}
