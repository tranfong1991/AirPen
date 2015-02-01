package com.example.rafa.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mSenAccelerometer;
    private Vibrator v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSenAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSenAccelerometer , SensorManager.SENSOR_DELAY_FASTEST);
        mSenAccelerometer = mSensorManager .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }


    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mSenAccelerometer, mSensorManager.SENSOR_DELAY_FASTEST);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER){

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float lastX = x;
            float lastY = y;
            float lastZ = z;

            TextView xCoord = (TextView) findViewById(R.id.x_axis);
            TextView yCoord = (TextView) findViewById(R.id.y_axis);
            TextView zCoord = (TextView) findViewById(R.id.z_axis);

            float deltaX = lastX - x;
            float deltaY = lastY - y;
            float deltaZ = lastZ - z;


            xCoord.setText(Double.toString(Math.round(x)*100/100.0));
            yCoord.setText(Double.toString(Math.round(y)*100/100.0));
            zCoord.setText(Double.toString(Math.round(z)*100/100.0));




        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
