package weather.com.weathertoday.models;

/**
 * Created by MMT3760 on 3/12/2016.
 */
public class AreaData {

    public Double temp;
    public Double pressure;
    public Integer humidity;
    public Double tempMin;
    public Double tempMax;
    public Double seaLevel;
    public Double grndLevel;

    @Override
    public String toString() {
        return "AreaData{" +
                "temp=" + temp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                ", seaLevel=" + seaLevel +
                ", grndLevel=" + grndLevel +
                '}';
    }
}
