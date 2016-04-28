package weather.com.weathertoday.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MMT3760 on 3/12/2016.
 */
public class WeatherDataByLatLong {

    public Coord coord;
    public List<Weather> weather = new ArrayList<Weather>();
    public String base;
    public AreaData main;
    public Wind wind;
    public Clouds clouds;
    public Integer dt;

    public Integer id;
    public String name;
    public Integer cod;
}
