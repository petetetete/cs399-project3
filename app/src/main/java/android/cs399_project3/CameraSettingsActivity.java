package android.cs399_project3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CameraSettingsActivity extends AppCompatActivity {

    private MainGlobal mainGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_settings);

        this.mainGlobal = ((MainGlobal) this.getApplication()); // Get global data

        getSupportActionBar().setTitle(mainGlobal.getCurrentCamera().getName() + " - settings");

        findViewById(R.id.save_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cameraName = ((EditText) findViewById(R.id.camera_name)).getText().toString();
                String cameraStatus = ((EditText) findViewById(R.id.camera_status)).getText().toString();
                int cameraStatusInt = (cameraStatus.equals("")) ? -1 : Integer.parseInt(cameraStatus);
                mainGlobal.editCurrentCameraSettings(cameraName, cameraStatusInt);

                Intent intent = new Intent(getApplicationContext(), CamerasActivity.class);
                startActivity(intent);
            }
        });
    }
}