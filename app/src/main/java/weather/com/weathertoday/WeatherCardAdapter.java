package weather.com.weathertoday;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import weather.com.weathertoday.models.WeatherDataByLatLong;

/**
 * Created by MMT3760 on 3/13/2016.
 */
public class WeatherCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int CARD_1 = 0;
    public static final int CARD_2 = 1;
    public static final int CARD_3 = 2;
    WeatherDataByLatLong weatherDataByLatLong;

    Context mContext;

    public WeatherCardAdapter(Context c, WeatherDataByLatLong weatherDataByLatLong) {
        this.mContext = c;
        this.weatherDataByLatLong = weatherDataByLatLong;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case CARD_1:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.general_temperature, parent, false);
                GeneralWeatherHolder vh = new GeneralWeatherHolder(v);
                return vh;
            case CARD_2:
                View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.humidity_pressure_card, parent, false);
                HumidityPressureHolder vh1 = new HumidityPressureHolder(v1);
                return vh1;
            case CARD_3:
                break;
            default:
                return null;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GeneralWeatherHolder) {
            if(null!=weatherDataByLatLong){
                Map<String, String> data = new HashMap<String, String>();
                if(null!=weatherDataByLatLong.coord){
                    data.put("Latitude", String.valueOf(weatherDataByLatLong.coord.lat));
                    data.put("Longitude", String.valueOf(weatherDataByLatLong.coord.lon));
                }
                if(null!=weatherDataByLatLong.main){
                    data.put("Pressure", String.valueOf(weatherDataByLatLong.main.pressure));
                    data.put("Humidity", String.valueOf(weatherDataByLatLong.main.humidity));
                    /*data.put("Sea Level", String.valueOf(weatherDataByLatLong.main.seaLevel));
                    data.put("Ground Level", String.valueOf(weatherDataByLatLong.main.grndLevel));*/
                }
                WeatherDataAdapter weatherDataAdapter = new WeatherDataAdapter(data);
                ((GeneralWeatherHolder) holder).tempData.setNestedScrollingEnabled(true);
                ((GeneralWeatherHolder) holder).tempData.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                ((GeneralWeatherHolder) holder).tempData.setAdapter(weatherDataAdapter);

            }
        }else if(holder instanceof HumidityPressureHolder){
            File file = getTempFile(mContext);
            Bitmap b = null;
            try {
                b = BitmapFactory.decodeStream(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if(null!=b) {
                ((HumidityPressureHolder) holder).pictureOfTheDay.setImageBitmap(b);
            }

        }
    }

    public File getTempFile(Context context) {
        File file = null;
        try {
            String fileName = Uri.parse("weather/imagehere.jpg").getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e) {
            // Error while creating file
        }
        return file;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    class GeneralWeatherHolder extends RecyclerView.ViewHolder{

        RecyclerView tempData;

        public GeneralWeatherHolder(View itemView) {
            super(itemView);
            tempData = (RecyclerView) itemView.findViewById(R.id.temp_data_list);
        }
    }

    class HumidityPressureHolder extends RecyclerView.ViewHolder{

        TextView text;
        ImageView pictureOfTheDay;

        public HumidityPressureHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.location_name);
            pictureOfTheDay = (ImageView) itemView.findViewById(R.id.pic_of_the_day);
        }
    }


}
