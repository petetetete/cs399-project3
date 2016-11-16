package android.cs399_project3;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    private MainGlobal mainGlobal;

    // Global variables for input fields
    private Spinner settingsNotification;
    private EditText phoneNumber;

    // Navigation drawer variables
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private String activityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mainGlobal = ((MainGlobal) this.getApplication()); // Get global data
        settingsNotification = (Spinner) findViewById(R.id.settings_notifications);
        phoneNumber = (EditText) findViewById(R.id.phone_number);

        // Populate spinner with options
        String[] notificationOptions = getResources().getStringArray(R.array.notification_options);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, notificationOptions);
        settingsNotification.setAdapter(adapter);

        // Fill in inputs with current data
        settingsNotification.setSelection(mainGlobal.getSettings().getNotifications() ? 0 : 1);

        // Initialize drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        activityTitle = getTitle().toString();
        setupNavDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Add event listener to save settings button
        findViewById(R.id.save_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get data from inputs
                String cS = settingsNotification.getSelectedItem().toString();
                boolean cSB = cS.equals(getResources().getStringArray(R.array.notification_options)[0]);
                String pN = phoneNumber.getText().toString();

                // Save settings and navigate back to list
                mainGlobal.editSettings(cSB, pN);
                Intent intent = new Intent(getApplicationContext(), CamerasActivity.class);
                startActivity(intent);
            }
        });
    }


    // Methods for navigation drawer
    private void setupNavDrawer() {

        ListView drawerList = (ListView) findViewById(R.id.nav_list);
        final String[] navOptions = getResources().getStringArray(R.array.nav_options);

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, navOptions);
        drawerList.setAdapter(mAdapter);

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(getApplicationContext(), CamerasActivity.class);
                    startActivity(intent);
                }
                if (position == 1) {
                    Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivity(intent);
                }
            }
        });

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(activityTitle);
                invalidateOptionsMenu();
            }
        };

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
