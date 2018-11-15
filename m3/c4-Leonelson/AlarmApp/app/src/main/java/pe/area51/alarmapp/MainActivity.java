package pe.area51.alarmapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.AlarmManagerCompat;
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
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final EditText editTextTimeInSeconds = findViewById(R.id.textAlarm);
        findViewById(R.id.buttonAlarm).setOnClickListener(view -> {
            final String timeInSecondsText = editTextTimeInSeconds
                    .getText()
                    .toString()
                    .trim();
            try {
                final int timeInSeconds = Integer.valueOf(timeInSecondsText);
                programAlarm(timeInSeconds);
            } catch (NumberFormatException e) {
                showErrorMessage(getString(R.string.error_no_number));
            }
        });
    }

    private void showErrorMessage(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void programAlarm(final int timeInSeconds) {
        final PendingIntent operation = PendingIntent.getActivity(
                this,
                1,
                new Intent(this, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        final int timeInMillis = timeInSeconds * 1000;
        AlarmManagerCompat.setExact(
                alarmManager,
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + timeInMillis,
                operation
        );
    }
}
