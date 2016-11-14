package android.cs399_project3;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class CameraSettingsActivity extends AppCompatActivity {

    private MainGlobal mainGlobal;

    // Global variables for input fields
    private EditText cameraName;
    private EditText cameraUrl;
    private Spinner cameraStatus;
    private Spinner cameraNotification;
    private TextView cameraStartTime;
    private TextView cameraEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_settings);

        mainGlobal = ((MainGlobal) this.getApplication()); // Get global data
        cameraName = (EditText) findViewById(R.id.camera_name);
        cameraUrl = (EditText) findViewById(R.id.camera_url);
        cameraStatus = (Spinner) findViewById(R.id.camera_status);
        cameraNotification = (Spinner) findViewById(R.id.camera_notifications);
        cameraStartTime = (TextView) findViewById(R.id.camera_start_time);
        cameraEndTime = (TextView) findViewById(R.id.camera_end_time);

        // Add required listener to camera status
        cameraName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!cameraName.getText().toString().equals("")) cameraName.setError(null);
                else cameraName.setError(getResources().getString(R.string.required_error));
            }
        });

        // Populate spinner with options
        String[] statusOptions = getResources().getStringArray(R.array.status_options);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, statusOptions);
        cameraStatus.setAdapter(adapter);

        // Populate spinner with options
        String[] notificationOptions = getResources().getStringArray(R.array.notification_options);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.spinner_item, notificationOptions);
        cameraNotification.setAdapter(adapter1);

        // Time picker listener
        View.OnClickListener timePicker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView textView = (TextView) v;
                String[] timeArray = textView.getText().toString().split(":");
                TimePickerDialog mTimePicker = new TimePickerDialog(CameraSettingsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        textView.setText(mainGlobal.formatTime(selectedHour, selectedMinute));
                    }
                }, Integer.parseInt(timeArray[0]), Integer.parseInt(timeArray[1]), true);
                mTimePicker.setTitle(getResources().getString(R.string.time_picker_title));
                mTimePicker.show();
            }
        };

        // Apply listener to the fields
        cameraStartTime.setOnClickListener(timePicker);
        cameraEndTime.setOnClickListener(timePicker);

        // Update action bar to include name of camera
        getSupportActionBar().setTitle(mainGlobal.getCurrentCamera().getName() + " - settings");

        // Fill in inputs with current data
        cameraName.setText(mainGlobal.getCurrentCamera().getName());
        cameraUrl.setText(mainGlobal.getCurrentCamera().getUrl());
        cameraStatus.setSelection(adapter.getPosition(mainGlobal.getStatusCurrentCamera()));
        cameraNotification.setSelection(mainGlobal.getCurrentCamera().getNotifications() ? 0 : 1);
        cameraStartTime.setText(mainGlobal.getCurrentCamera().getStartTime());
        cameraEndTime.setText(mainGlobal.getCurrentCamera().getEndTime());

        // Add event listener to save camera button
        findViewById(R.id.save_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get data from inputs
                String cN = cameraName.getText().toString();
                int cS = cameraStatus.getSelectedItemPosition();
                String cSN = cameraNotification.getSelectedItem().toString();
                boolean cSNB = cSN.equals(getResources().getStringArray(R.array.notification_options)[0]);
                String cU = cameraUrl.getText().toString();
                String cST = cameraStartTime.getText().toString();
                String cET = cameraEndTime.getText().toString();

                // Check if a name was entered, if so, navigate back to list
                if (!cN.equals("")) {
                    mainGlobal.getCurrentCamera().addLogEntry("Settings updated.");
                    mainGlobal.editCurrentCameraSettings(cN, cS, cSNB, cU, cST, cET);
                    Intent intent = new Intent(getApplicationContext(), CamerasActivity.class);
                    startActivity(intent);
                }
                else cameraName.setError(getResources().getString(R.string.required_error));
            }
        });
    }
}