package weather.com.weathertoday.models;

/**
 * Created by MMT3760 on 3/12/2016.
 */
public class WeatherData {

    public Double temp;
    public Double tempMin;
    public Double tempMax;
    public Double pressure;
    public Double seaLevel;
    public Double grndLevel;
    public Integer humidity;

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(Double seaLevel) {
        this.seaLevel = seaLevel;
    }

    public Double getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(Double grndLevel) {
        this.grndLevel = grndLevel;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "temp=" + temp +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                ", pressure=" + pressure +
                ", seaLevel=" + seaLevel +
                ", grndLevel=" + grndLevel +
                ", humidity=" + humidity +
                '}';
    }
}
