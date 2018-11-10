package pe.area51.locationapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private TextView textViewLocation;
    private LocationManager locationManager;
    private final static int REQUEST_CODE_LOCATION_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewLocation = findViewById(R.id.textViewLocation);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (hasLocationPermission()) {
            startLocationRetrieving();
        } else {
            requestLocationPermission();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationRetrieving();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationRetrieving();
            return;
        }
        finish();
    }

    @SuppressLint("MissingPermission")
    private void startLocationRetrieving() {
        final List<String> locationProviders = locationManager.getAllProviders();
        for (final String provider : locationProviders) {

            locationManager.requestLocationUpdates(
                    provider,
                    0,
                    0,
                    this
            );
        }

    }

    private void stopLocationRetrieving() {
        locationManager.removeUpdates(this);
    }

    private boolean hasLocationPermission() {
        return ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_CODE_LOCATION_PERMISSION
        );
    }

    @Override
    public void onLocationChanged(Location location) {
        textViewLocation.setText(location.toString());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
