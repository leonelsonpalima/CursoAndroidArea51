package pe.area51.camaraweb;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ImageView imageViewPreview;

    private final static int REQUEST_CODE_TAKE_PICTURE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageViewPreview = findViewById(R.id.imageViewPreview);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuCamara) {
            takePicture();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE_PICTURE && requestCode == RESULT_OK) {
            Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();
        }
    }

    private void takePicture() {
        dispatchCameraIntent();
    }

    private void dispatchCameraIntent() throws IOException {
        final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (!doesActivityExist(cameraIntent)) {
            showErrorMessage("Camera not found");
            return;
        }

        final Uri pictureUri;
        pictureUri = createPictureContentUri(createPictureTempFile());
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        //cameraIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        //cameraIntent.setData(Uri.fromFile(createPictureTempFile()));
        startActivityForResult(cameraIntent, REQUEST_CODE_TAKE_PICTURE);
    }

    private boolean doesActivityExist(final Intent intent) {
        return intent.resolveActivity(getPackageManager()) != null;
    }

    private void showErrorMessage(final String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private File createPictureTempFile() throws IOException {
        final File cacheDir = getCacheDir();
        final File picDir = new File(cacheDir.getAbsolutePath() + "/pics");
        picDir.mkdirs();
        final String tempFileName = "pic_" + System.currentTimeMillis();
        File.createTempFile(tempFileName, "", cacheDir);
        return cacheDir;
    }

    private Uri createPictureContentUri(final File pictureFile) {
        return FileProvider.getUriForFile(this, "pe.area51.camaraweb.PictureFiles", pictureFile);
    }
}
