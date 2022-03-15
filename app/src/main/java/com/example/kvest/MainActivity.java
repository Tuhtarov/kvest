package com.example.kvest;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.geo.GeoPoint;
import com.geo.GeoPointsMock;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GpsTracker gpsTracker;
    private TextView latitude, longitude, distanceTo, questName, questDescription;
    private Button getLocationBtn;

    private final Handler mHandler = new Handler();

    private double currentLatitude;
    private double currentLongitude;

    private Location currentLocation;
    private Location questLocation;

    // счетчик времени
    private final long mTime = 0L;

    public void initView() {
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        distanceTo = findViewById(R.id.distanceToQuest);
        getLocationBtn = findViewById(R.id.GetLocationBtn);
        questName = findViewById(R.id.questName);
        questDescription = findViewById(R.id.questDescription);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //mTime = SystemClock.uptimeMillis();
        mHandler.removeCallbacks(timeUpdaterRunnable);

        // Добавляем Runnable-объект timeUpdaterRunnable в очередь
        // сообщений, объект должен быть запущен после задержки в 100 мс
        mHandler.postDelayed(timeUpdaterRunnable, 250);

        questLocation = initGuestLocation();
        currentLocation = initCurrentLocation();

        getLocationBtn.setOnClickListener(getLocationListener);

        fetchAccessLocation();
    }

    public Location initGuestLocation() {
        Location location = new Location("Point B");

//        GeoPoint guestGeoPoint = new GeoPoint(53.726479, 91.426739);

        GeoPoint geopoint = (new GeoPointsMock()).getRandomGeoPoint();
        Quest questHome = new Quest("GoToHome", "Хочу домой", geopoint);

        questName.setText(String.valueOf(questHome.getName()));
        questDescription.setText(String.valueOf(questHome.getDescription()));

        location.setLatitude(questHome.getLatitude());
        location.setLongitude(questHome.getLongitude());
        return location;
    }

    public Location initCurrentLocation() {
        return new Location("Point A");
    }

    public void getLocation(){
        gpsTracker = new GpsTracker(MainActivity.this);

        if (gpsTracker.canGetLocation()){
            currentLocation.setLatitude(gpsTracker.getLatitude());
            currentLocation.setLongitude(gpsTracker.getLongitude());

            latitude.setText(String.valueOf(currentLocation.getLatitude()));
            longitude.setText(String.valueOf(currentLocation.getLongitude()));
        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    View.OnClickListener getLocationListener = v -> {
        currentLocation.setLatitude(currentLatitude);
        currentLocation.setLongitude(currentLongitude);
        //currentLatitude, currentLongitude, questHome.getQuestLatitude(), questHome.getQuestLongitude()
        getDistanceBetween(currentLocation, questLocation);
    };

    //double currentLatitude, double currentLongitude, double questLatitude, double questLongitude
    public void getDistanceBetween(Location currentLocation, Location questLocation)
    {
        double distanceTo = currentLocation.distanceTo(questLocation);
        this.distanceTo.setText(String.valueOf(distanceTo));
    }

    private final Runnable timeUpdaterRunnable = new Runnable() {
        public void run() {
            getLocation();
            getDistanceBetween(currentLocation, questLocation);
            mHandler.postDelayed(this, 2000);
        }
    };

    private void fetchAccessLocation() {
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

