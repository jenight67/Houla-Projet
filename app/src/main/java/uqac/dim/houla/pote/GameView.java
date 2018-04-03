package uqac.dim.houla.pote;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import uqac.dim.houla.R;

public class GameView extends AppCompatActivity {
    private SensorManager sm;
    private float acelVal; // current acceleration including gravity
    private float acelLast; // last acceleration including gravity
    private float shake; // acceleration apart from gravity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pote_jeu);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        shake = 0.00f;
        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
    }
    private final SensorEventListener sensorListener=new SensorEventListener() {
        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            acelLast = acelVal;
            acelVal = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = acelVal-acelLast;
            shake = shake * 0.9f + delta; // perform low-cut filter
            if (shake >12) {
                Toast toast =Toast.makeText(getApplicationContext(), "ARRETE DE SECOUER PD", Toast.LENGTH_LONG);
                toast.show();
            }
        }
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
}