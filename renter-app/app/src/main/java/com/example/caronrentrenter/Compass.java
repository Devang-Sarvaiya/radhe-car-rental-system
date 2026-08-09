package com.example.caronrentrenter;


import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Compass extends AppCompatActivity implements SensorEventListener  {
    ImageView compass;
    TextView txtdegree;
    SensorManager sensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        compass=findViewById(R.id.compass);
        txtdegree=findViewById(R.id.degree);
        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    protected void onResume() {
        super.onResume();
        List<Sensor> sensorList=sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s:sensorList){
            Log.d("Sensor",s.toString());
        }
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), sensorManager.SENSOR_DELAY_UI);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int degree=Math.round(sensorEvent.values[0]);
        txtdegree.setText("Degree : "+degree);
        compass.setRotation(-degree);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}