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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameras);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable title back navigation

        // Set FAB action
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Update with activity navigation
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Generate camera list
        ArrayList<String> list = new ArrayList<String>();
        list.add("Camera 1");
        list.add("Camera 2");
        list.add("Camera 3");
        list.add("Camera 4");
        list.add("Camera 5");

        // Instantiate camera adapter
        CameraAdapter adapter = new CameraAdapter(list, this);

        // Handle camera_list
        ListView lView = (ListView)findViewById(R.id.camera_list);
        lView.setAdapter(adapter);
    }

}
