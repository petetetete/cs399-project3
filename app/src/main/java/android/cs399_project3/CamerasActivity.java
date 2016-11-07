package android.cs399_project3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class CamerasActivity extends AppCompatActivity {

    private MainGlobal mainGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameras);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable title back navigation
        this.mainGlobal = ((MainGlobal) this.getApplication());

        // Set FAB action
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewCameraActivity.class);
                startActivity(intent);
            }
        });

        // Instantiate camera adapter
        CameraAdapter adapter = new CameraAdapter(mainGlobal.getCameras(), this);

        // Handle camera_list
        ListView lView = (ListView)findViewById(R.id.camera_list);
        lView.setAdapter(adapter);
    }

}
