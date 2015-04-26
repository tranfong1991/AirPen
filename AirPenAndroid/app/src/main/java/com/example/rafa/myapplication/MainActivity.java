package com.example.rafa.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import java.math.*;

public class MainActivity extends ActionBarActivity implements SensorEventListener, View.OnClickListener{

    private Button draw;
    private Button red;
    private Button yellow;
    private Button blue;
    private SensorManager mSensorManager;
    private Sensor mSenAccelerometer;
    

    private Socket socket;
    private PrintWriter out;
    private InetAddress adr;
    private boolean on = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //needed to use accelerometer
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSenAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSenAccelerometer , SensorManager.SENSOR_DELAY_UI);
        mSenAccelerometer = mSensorManager .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        
        /*this takes care of threading our request. we need to be able to interact with ui while
        it communicates and sends info to the server*/
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params){
                try {
                    adr = InetAddress.getByName("192.168.137.98");
                    socket = new Socket(adr, 1008);
                    out = new PrintWriter(socket.getOutputStream(), true);
                } catch (IOException e){
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(null, null, null);

        mSensorManager.registerListener(this, mSenAccelerometer , SensorManager.SENSOR_DELAY_UI);
        mSenAccelerometer = mSensorManager .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        draw = (Button) findViewById(R.id.draw);
        red = (Button) findViewById(R.id.red);
        yellow = (Button) findViewById(R.id.yellow);
        blue = (Button) findViewById(R.id.blue);

        red.setOnClickListener(this);
        blue.setOnClickListener(this);
        yellow.setOnClickListener(this);
        draw.setOnClickListener(this);




    }
    //we are passing color values to the server which get parsed and displayed on the desktop
    // c means "color" and either "red" "yellow" or "blue" follow
    @Override
    public void onClick(View v){
        int id = v.getId();
        switch(id){
            case R.id.red:
                out.println("c red");
                break;
            case R.id.yellow:
                out.println("c yellow");
                break;
            case R.id.blue:
                out.println("c blue");
                break;
            case R.id.draw:
                if (on == false) {
                    on = true;
                    out.println("on");
                } else {
                    on = false;
                    out.println("off");
                }
                break;
        }

    }
    
    //make sure we close the socket when application stops
    @Override
    protected void onStop(){
        super.onStop();
        mSensorManager.unregisterListener(this);
        try{
            socket.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    @Override
    protected void onPause(){
        super.onPause();


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

            final float alpha = 0.1f;
            float[] gravity = new float[3];

           
            //attempt at low pass filter
            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];



            //my attemp at  better solution than the low pass filter
            /*int xd = (int)(-1 * (Math.pow(event.values[0], 3))) + 600;
            int yd = (int)((Math.pow(event.values[1], 3))) + 250;*/
            
            //this is where we pass the accelerometer data to the server
           if(out != null){
                out.println(xd +"," + yd);
                out.flush();
            }  else{
                System.out.println("out is null");
            }

            TextView xCoord = (TextView) findViewById(R.id.x_axis);
            TextView yCoord = (TextView) findViewById(R.id.y_axis);
            
            //or maybe this is aaron's better solution.
            xCoord.setText(Integer.toString((int)(-1 * (Math.pow(event.values[0], 3)))));
            yCoord.setText(Integer.toString((int)(-1 * (Math.pow(event.values[1], 3)))));




        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
