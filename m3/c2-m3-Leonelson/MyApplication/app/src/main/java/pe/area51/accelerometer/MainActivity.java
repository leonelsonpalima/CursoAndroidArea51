package pe.area51.accelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView textViewSensorData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSystemService(SENSOR_SERVICE);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        textViewSensorData = findViewById(R.id.textViewSensorData);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_FASTEST
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        final String sensorData =
                String.format("X:%s\n Y:%s\n Z:%s",
                        String.valueOf(sensorEvent.values[0]),
                        String.valueOf(sensorEvent.values[1]),
                        String.valueOf(sensorEvent.values[2]));
        textViewSensorData.setText(sensorData);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
