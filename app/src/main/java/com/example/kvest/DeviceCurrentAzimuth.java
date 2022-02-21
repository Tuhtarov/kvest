package com.example.kvest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class DeviceCurrentAzimuth implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    private int azimuthFrom = 0 ;
    private int azimuthTo = 0 ;
    private OnAzimuthChangedListener mAzimuthListener;
    Context mContext ;

    public  DeviceCurrentAzimuth(OnAzimuthChangedListener mAzimuthListener, Context context)
    {
        this.mAzimuthListener = mAzimuthListener;
        mContext=context;
    }
    public void StartSensorListener()
    {
        sensorManager = (SensorManager) mContext.getSystemService(mContext.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_UI);
    }
    public void StopSensorListener()
    {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        azimuthFrom = azimuthTo;

        //массив из трех элементов типа float, в который запишутся углы наклона аппарата в радианах
        float[] orientationCoordinates = new float[3];
        //матрица поворота устройства
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector (rotationMatrix, sensorEvent.values);
        azimuthTo = (int) (Math.toDegrees(SensorManager.getOrientation(rotationMatrix, orientationCoordinates)[0]) + 360 ) % 360 ;

        mAzimuthListener.onAzimuthChanged( azimuthFrom , azimuthTo );
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
