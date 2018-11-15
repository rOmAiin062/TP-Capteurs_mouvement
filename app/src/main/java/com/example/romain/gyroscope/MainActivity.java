package com.example.romain.gyroscope;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;

import static java.lang.Math.round;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // Dimension de l'écran
    private int screen_witdh, screen_height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupération des dimensions de l'écran
        getScreenDimension();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    /* Implémentation des méthodes de l'interface SensorEventListener */

    /**
     * Appelé lorsque la précision du capteur enregistré a changé.
     *
     * @param sensor   Sensor
     * @param accuracy Nouvelle précision du capteur
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Méthode appelé lorsque qu'il y a un nouvel événement avec le capteur
     *
     * @param event the SensorEvent.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
    }

    /**
     * Méthode qui récupére les dimensions de l'écran
     */
    public void getScreenDimension() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        this.screen_witdh = size.x;
        this.screen_height = size.y;
    }
}



