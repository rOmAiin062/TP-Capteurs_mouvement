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

    // Gestionnaire de capteurs
    private SensorManager mSensorManager;

    // Capteur 'Accélérometre'
    private Sensor sensorAccelerometer;
    // Capteur 'Gyroscope'
    private Sensor sensorGyroscope;

    // Derniere position de l'image
    private float last_x, last_y;

    // Dimension de l'écran
    private int screen_witdh, screen_height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instanciation du gestionnaire de capteurs
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Instanciation des deux capteurs utilisés (Accéléromètre + Gyroscope)
        sensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // Récupération des dimensions de l'écran
        getScreenDimension();

    }

    @Override
    protected void onPause() {
        super.onPause();

        // On se "désabonne" des capteurs - si un événement arrive, on ne s'en occupe plus
        mSensorManager.unregisterListener(this, sensorAccelerometer);
        mSensorManager.unregisterListener(this, sensorGyroscope);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // On s'abonne aux capteurs - si un événement arrive, on le récupére et on le traite
        mSensorManager.registerListener(this, sensorAccelerometer, mSensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, sensorGyroscope, mSensorManager.SENSOR_DELAY_UI);
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
        // Cas 1 : On traite un événement venant du capteur 'Accéléromètre'
        if (event.sensor.getType() == sensorAccelerometer.TYPE_ACCELEROMETER) {
            // Récupération des valeurs du capteur 'Accéléromètre'
            float axe_x = event.values[0]; // axe x
            float axe_y = event.values[1]; // axe y
            float axe_z = event.values[2]; // axe z

            // Affichage des valeurs récupérées ci-dessus
            TextView tx = (TextView) findViewById(R.id.accelerometre_x);
            TextView ty = (TextView) findViewById(R.id.accelerometre_y);
            TextView tz = (TextView) findViewById(R.id.accelerometre_z);
            tx.setText(round(axe_x) + "m/s^2");
            ty.setText(round(axe_y) + "m/s^2");
            tz.setText(round(axe_z) + "m/s^2");

            // Animation de l'image en fonction des valeurs récupérées
            ImageView img = (ImageView) findViewById(R.id.imageView);
            last_x -= axe_x;
            last_y += axe_y;

            // On met à jour l'image sur l'écran
            if((0 <= last_x) && (last_x <= screen_witdh - 40)){
                img.setX(last_x);
            }
            if((0 <= last_y) && (last_y <= screen_height - 40)){
                img.setY(last_y);
            }
        }
        // Cas 2 : On traite un événement venant du capteur 'Gyroscope'
        else if(event.sensor.getType() == sensorGyroscope.TYPE_GYROSCOPE){
            // Récupération des valeurs du capteur 'Gyroscope'
            float vitesse_x = event.values[0]; // vitesse angulaire autour de x
            float vitesse_y = event.values[1]; // vitesse angulaire autour de y
            float vitesse_z = event.values[2]; // vitesse angulaire autour de z

            // Affichage des valeurs récupérées ci-dessus
            TextView tx = (TextView) findViewById(R.id.gyro_x);
            TextView ty = (TextView) findViewById(R.id.gyro_y);
            TextView tz = (TextView) findViewById(R.id.gyro_z);
            tx.setText(round(vitesse_x) + "rad/s");
            ty.setText(round(vitesse_y) + "rad/s");
            tz.setText(round(vitesse_z) + "rad/s");
        }
        else {
            return;
        }
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



