package pe.area51.timecounter;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private final static long REPETITIVE_TASK_DELAY_IN_MILLIS = 10;

    private TextView textViewStatus;

    private RepetitiveTask timeCounterRepetitiveTask;

    private long startTime;

    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewStatus = findViewById(R.id.textview_status);
        timeCounterRepetitiveTask = createTimeCounterRepetitiveTask();
        startTime = 0;
        decimalFormat = new DecimalFormat("0.000");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_switch_timer:
                toggleTimeCounter(item);
                return true;
            default:
                return false;
        }
    }

    private void toggleTimeCounter(final MenuItem menuItem) {
        if (timeCounterRepetitiveTask.isRunning()) {
            timeCounterRepetitiveTask.stop();
            menuItem.setIcon(R.drawable.ic_action_start_timer);
            menuItem.setTitle(R.string.start_timer);
        } else {
            startTime = SystemClock.elapsedRealtime();
            timeCounterRepetitiveTask.start(true);
            menuItem.setIcon(R.drawable.ic_action_stop_timer);
            menuItem.setTitle(R.string.stop_timer);
        }
    }

    private RepetitiveTask createTimeCounterRepetitiveTask() {
        return new RepetitiveTask(new Runnable() {
            @Override
            public void run() {
                final long elapsedTimeInMillis = (SystemClock.elapsedRealtime() - startTime);
                updateTextViewStatus(elapsedTimeInMillis);
            }
        }, REPETITIVE_TASK_DELAY_IN_MILLIS);
    }

    private void updateTextViewStatus(final long elapsedTimeInMillis) {
        final String formattedElapsedTime = decimalFormat.format(elapsedTimeInMillis / 1000.0);
        textViewStatus.setText(formattedElapsedTime);
    }
}