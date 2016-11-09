package android.cs399_project3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CameraSettingsActivity extends AppCompatActivity {

    private MainGlobal mainGlobal;

    private EditText cameraName;
    private EditText cameraStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_settings);

        this.mainGlobal = ((MainGlobal) this.getApplication()); // Get global data

        getSupportActionBar().setTitle(mainGlobal.getCurrentCamera().getName() + " - settings");

        cameraName = (EditText) findViewById(R.id.camera_name);
        cameraStatus = (EditText) findViewById(R.id.camera_status);

        cameraName.setText(mainGlobal.getCurrentCamera().getName());
        cameraStatus.setText(Integer.toString(mainGlobal.getCurrentCamera().getStatus()));

        findViewById(R.id.save_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cN = cameraName.getText().toString();
                String cS = cameraStatus.getText().toString();
                int cSI = (cS.equals("")) ? -1 : Integer.parseInt(cS);
                mainGlobal.addCamera(cN, cSI);

                Intent intent = new Intent(getApplicationContext(), CamerasActivity.class);
                startActivity(intent);
            }
        });
    }
}