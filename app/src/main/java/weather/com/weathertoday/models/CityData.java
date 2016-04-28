package weather.com.weathertoday.models;

import java.util.ArrayList;
import java.util.List;


public class CityData {

    public String message;
    public String cod;
    public Integer count;
    public String name;
    public List<CityList> list = new ArrayList<CityList>();


    @Override
    public String toString() {
        return "CityData{" +
                "message='" + message + '\'' +
                ", cod='" + cod + '\'' +
                ", count=" + count +
                ", list=" + list +
                '}';
    }
}