package weather.com.weathertoday;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by MMT3760 on 3/16/2016.
 */
public class PicOfDay extends Activity {

    private Handler mHandler = new Handler();
    boolean swap = true;
    ImageView picOfTheDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pic_of_day);
        picOfTheDay = (ImageView) findViewById(R.id.todays_pic);
        setUpPictureAlarm();
    }

    private void setUpPictureAlarm() {
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(myRunnable);
            }
        }, 0, 3000);

    }

    final Runnable myRunnable = new Runnable() {
        public void run() {
            if(swap) {
                picOfTheDay.setImageResource(R.drawable.bg_haze);
            }else{
                picOfTheDay.setImageResource(R.drawable.bg_winter);
            }
            swap=!swap;
        }
    };

}
