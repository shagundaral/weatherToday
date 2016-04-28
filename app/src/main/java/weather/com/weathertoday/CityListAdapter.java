package weather.com.weathertoday;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MMT3760 on 3/12/2016.
 */
public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {

    List<String> cityList;
    Context mContext;

    public CityListAdapter(List<String> cities, Context context){
        this.cityList = cities;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.cityName.setText(cityList.get(position));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = holder.cityName.getText().toString();
                Intent intent = new Intent(mContext, CityWeatherTab.class);
                intent.putExtra("city", city);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView cityName;
        RelativeLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            cityName = (TextView) itemView.findViewById(R.id.city_name);
            layout = (RelativeLayout) itemView.findViewById(R.id.city_layout);
        }
    }

}
