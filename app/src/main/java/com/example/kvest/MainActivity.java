package com.example.kvest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {


    private GpsTracker gpsTracker;
    private TextView tvLatitude, tvLongitude,tvDistanceTo, tvQuestName, tvQuestDescription;
    private Button getLocationBtn;
    private View viewA;
    private Handler mHandler = new Handler();

    private double currentLatitude;
    private double currentLongitude;

    private Location currentLocation;
    private Location questLocation;

    // счетчик времени
    private long mTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLatitude = (TextView) findViewById(R.id.latitude);
        tvLongitude = (TextView) findViewById(R.id.longitude);
        tvDistanceTo = (TextView) findViewById(R.id.distanceToQuest);
        getLocationBtn = (Button) findViewById(R.id.GetLocationBtn);
        tvQuestName = (TextView) findViewById(R.id.questName);
        tvQuestDescription = (TextView) findViewById(R.id.questDescription);


            //mTime = SystemClock.uptimeMillis();
            mHandler.removeCallbacks(timeUpdaterRunnable);
            // Добавляем Runnable-объект timeUpdaterRunnable в очередь
            // сообщений, объект должен быть запущен после задержки в 100 мс
            mHandler.postDelayed(timeUpdaterRunnable, 250);


            final double QUEST_LATITUDE_EXAMPLE = 53.726479;
            final double QUEST_LONGITUDE_EXAMPLE = 91.426739;

            Quest questHome = new Quest("GoToHome", "Хочу домой",QUEST_LATITUDE_EXAMPLE,QUEST_LONGITUDE_EXAMPLE);
            tvQuestName.setText(String.valueOf(questHome.getQuestName()));
            tvQuestDescription.setText(String.valueOf(questHome.getQuestDescription()));
            currentLocation = new Location("Point A");


            questLocation = new Location("Point B");
            questLocation.setLatitude(questHome.getQuestLatitude());
            questLocation.setLongitude(questHome.getQuestLongitude());

            viewA = new View(this);
        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
currentLocation.setLatitude(currentLatitude);
currentLocation.setLongitude(currentLongitude);
//currentLatitude, currentLongitude, questHome.getQuestLatitude(), questHome.getQuestLongitude()
getDistanceBetween(currentLocation, questLocation);
            }
        });
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void getLocation(){
        gpsTracker = new GpsTracker(MainActivity.this);
        if(gpsTracker.canGetLocation()){
            currentLocation.setLatitude(gpsTracker.getLatitude());
            currentLocation.setLongitude(gpsTracker.getLongitude());
            tvLatitude.setText(String.valueOf(currentLocation.getLatitude()));
            tvLongitude.setText(String.valueOf(currentLocation.getLongitude()));
        }else{
            gpsTracker.showSettingsAlert();
        }
    }
    //double currentLatitude, double currentLongitude, double questLatitude, double questLongitude
public void getDistanceBetween(Location currentLocation, Location questLocation)
{

    //double distanceBetween = (double)
    double distanceTo = (double) currentLocation.distanceTo(questLocation);
    tvDistanceTo.setText(String.valueOf(distanceTo));
}

    private Runnable timeUpdaterRunnable = new Runnable() {
        public void run() {

            getLocation();
            getDistanceBetween(currentLocation, questLocation);
            mHandler.postDelayed(this, 2000);
        }
    };

}

