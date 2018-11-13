package pe.area51.alarmapp;

import android.app.AlarmManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AlarmManager alarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmManager = (AlarmManager) getSystemService( ALARM_SERVICE);
        final EditText editTextTimeInSeconds = findViewById(R.id.editTextTimeInSeconds);
        findViewById(R.id.buttonProgramAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String timeInSecondsText = editTextTimeInSeconds.getText().toString();
                try {
                    final int timeInSeconds = Integer.valueOf(timeInSecondsText);
                }catch (NumberFormatException e){
                    Toast.makeText(MainActivity.this,
                            "Enter a number",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private final void programAlarm(final int timeInSeconds){
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +10000);
    }
}
