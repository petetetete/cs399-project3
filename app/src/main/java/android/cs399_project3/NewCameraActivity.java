package android.cs399_project3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewCameraActivity extends AppCompatActivity {

    private MainGlobal mainGlobal;

    // Global variables for input fields
    private EditText cameraName;
    private EditText cameraStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_camera);

        mainGlobal = ((MainGlobal) this.getApplication()); // Get global data
        cameraName = (EditText) findViewById(R.id.camera_name);
        cameraStatus = (EditText) findViewById(R.id.camera_status);

        // Add event listener to add camera button
        findViewById(R.id.add_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cN = cameraName.getText().toString();
                String cS = cameraStatus.getText().toString();
                int cSI = (cS.equals("")) ? -1 : Integer.parseInt(cS);
                mainGlobal.addCamera(cN, cSI, "https://ia902702.us.archive.org/17/items/JerkyTurkey1945/Jerky_Turkey_1945_512kb.mp4");

                Intent intent = new Intent(getApplicationContext(), CamerasActivity.class);
                startActivity(intent);
            }
        });
    }
}