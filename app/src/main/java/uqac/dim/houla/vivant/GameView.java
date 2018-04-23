package uqac.dim.houla.vivant;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import uqac.dim.houla.R;

public class GameView extends AppCompatActivity {
    // Valeur courante de la proximité
    float p;
    // Le sensor manager
    SensorManager sm;

    private boolean partieEnCours;
    private boolean gagne=false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vivant_jeu);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);

        //On récupère le layout de fin
        //LinearLayout layoutFin = findViewById(R.id.layoutFin);
        //On masque le layout de fin
        //layoutFin.setAlpha(0.0f);
    }

    private final SensorEventListener sensorListener=new SensorEventListener() {
        public void onSensorChanged(SensorEvent se) {
            if (se.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                // La valeur de la lumière
                p = se.values[0];
            }
        }
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
}