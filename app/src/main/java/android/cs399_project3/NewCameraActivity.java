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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable title back navigation
        this.mainGlobal = ((MainGlobal) this.getApplication());

        findViewById(R.id.AddCameraButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CamerasActivity.class);
                startActivity(intent);
                mainGlobal.addCamera(((EditText) findViewById(R.id.CameraNameEdit)).getText().toString());
            }
        });
    }
}