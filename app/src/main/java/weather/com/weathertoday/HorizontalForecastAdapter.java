package weather.com.weathertoday;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MMT3760 on 3/13/2016.
 */
public class HorizontalForecastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String[] forecastDummyData = {"Sun-21","Mon-23","Tue-23","Wed-23","Thu-24","Fri-23","Sat-23"};


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_forecast, parent, false);
        ForecastViewHolder vh = new ForecastViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ForecastViewHolder){
            ((ForecastViewHolder) holder).day.setText(forecastDummyData[position].split("-")[0]);
            ((ForecastViewHolder) holder).temp.setText(forecastDummyData[position].split("-")[1]);
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    class ForecastViewHolder extends RecyclerView.ViewHolder{

        TextView day, temp;
        ImageView tempIcon;

        public ForecastViewHolder(View v) {
            super(v);
            day = (TextView) v.findViewById(R.id.day);
            temp = (TextView) v.findViewById(R.id.temp);
            tempIcon = (ImageView) v.findViewById(R.id.icon_temp);
        }
    }

}
