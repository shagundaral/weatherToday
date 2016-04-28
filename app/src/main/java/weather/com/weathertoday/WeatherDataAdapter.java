package weather.com.weathertoday;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by MMT3760 on 3/13/2016.
 */
public class WeatherDataAdapter extends RecyclerView.Adapter<WeatherDataAdapter.ViewHolder> {

    Map<String, String> data;
    List<String> dataKeys;

    WeatherDataAdapter(Map<String, String> data){
        this.data = data;
        dataKeys = new ArrayList<>();
        for (String key:data.keySet()) {
            dataKeys.add(key);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_data_key_val, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String key = dataKeys.get(position);
        holder.key.setText(key);
        holder.val.setText(data.get(key));
    }

    @Override
    public int getItemCount() {
        return dataKeys.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView key, val;

        public ViewHolder(View itemView) {
            super(itemView);
            key = (TextView) itemView.findViewById(R.id.weather_data_key);
            val = (TextView) itemView.findViewById(R.id.weather_data_val);
        }
    }
}
