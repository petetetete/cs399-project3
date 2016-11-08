package android.cs399_project3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewCameraActivity extends AppCompatActivity {

    private MainGlobal mainGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_camera);

        this.mainGlobal = ((MainGlobal) this.getApplication()); // Get global data

        findViewById(R.id.add_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cameraName = ((EditText) findViewById(R.id.camera_name)).getText().toString();
                String cameraStatus = ((EditText) findViewById(R.id.camera_status)).getText().toString();
                int cameraStatusInt = (cameraStatus.equals("")) ? -1 : Integer.parseInt(cameraStatus);
                mainGlobal.addCamera(cameraName, cameraStatusInt);

                Intent intent = new Intent(getApplicationContext(), CamerasActivity.class);
                startActivity(intent);
            }
        });
    }
}