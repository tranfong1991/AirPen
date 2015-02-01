package com.example.rafa.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;



public class MainActivity extends ActionBarActivity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mSenAccelerometer;
    private Vibrator v;

    private Socket socket;
    private PrintWriter out;
    private InetAddress adr;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSenAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this, mSenAccelerometer , SensorManager.SENSOR_DELAY_UI);
        mSenAccelerometer = mSensorManager .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params){
                try {
                    adr = InetAddress.getByName("192.168.137.249");
                    socket = new Socket(adr, 1008);
                    out = new PrintWriter(socket.getOutputStream(), true);
                } catch (IOException e){
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(null, null, null);



        if(socket == null) {
            Toast.makeText(this, "Socket is null", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Socket is not null", Toast.LENGTH_LONG).show();
        }


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

        mSensorManager.registerListener(this, mSenAccelerometer, mSensorManager.SENSOR_DELAY_UI);

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER){

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            int xd = Math.round(x * 1000);
            int yd = Math.round(y * 1000);

           if(out != null){

               /*long beginTime = System.currentTimeMillis();

               long endTime = System.currentTimeMillis();
               long tDelta = endTime - beginTime;
               double elapsedSeconds = tDelta;*/



                   out.println(xd +"," + yd);
                   out.flush();
            }  else{
                System.out.println("out is null");
            }

            TextView xCoord = (TextView) findViewById(R.id.x_axis);
            TextView yCoord = (TextView) findViewById(R.id.y_axis);

            xCoord.setText(Double.toString(Math.round(x * 1000)));
            yCoord.setText(Double.toString(Math.round(y * 1000)));





        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
