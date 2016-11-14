package android.cs399_project3;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class MainGlobal extends Application {

    private boolean dataLoaded = false;

    private ArrayList<Camera> cameras;
    private Settings settings;
    private int currCamIndex = -1;

    // Constructor
    public MainGlobal() {
        this.cameras = new ArrayList<>();
        this.settings = new Settings();
    }

    // Helper methods
    public boolean needsLoad() {
        return !dataLoaded;
    }

    public void saveData() {
        try {
            JSONArray cameraData = new JSONArray();
            JSONObject camera;
            JSONArray cameraLog;

            for (Camera cam : cameras) {
                camera = new JSONObject();
                camera.put("name", cam.getName());
                camera.put("status", cam.getStatus());
                camera.put("notifications", cam.getNotifications());
                camera.put("url", cam.getUrl());
                camera.put("startTime", cam.getStartTime());
                camera.put("endTime", cam.getEndTime());
                cameraLog = new JSONArray();
                for (String log : cam.getLog()) cameraLog.put(log);
                camera.put("log", cameraLog);

                cameraData.put(camera);
            }

            String cameraDataString = cameraData.toString();

            SharedPreferences.Editor prefEditor = getSharedPreferences("appData", Context.MODE_PRIVATE).edit();
            prefEditor.putString("cameras", cameraDataString);
            prefEditor.apply();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        try {
            SharedPreferences sharedPref = getSharedPreferences("appData", Context.MODE_PRIVATE);
            String cameraJSON = sharedPref.getString("cameras", "");

            JSONArray cameraData = new JSONArray(cameraJSON);
            for (int i = 0; i < cameraData.length(); i++) {
                JSONObject camObject = cameraData.getJSONObject(i);

                String camName = camObject.getString("name");
                int camStatus = camObject.getInt("status");
                boolean camNotifications = camObject.getBoolean("notifications");
                String camUrl = camObject.getString("url");
                String camStartTime = camObject.getString("startTime");
                String camEndTime = camObject.getString("endTime");
                ArrayList<String> camLog = new ArrayList<>();

                JSONArray camLogJson = camObject.getJSONArray("log");
                for (int j = 0; j < camLogJson.length(); j++) {
                    camLog.add(camLogJson.get(j).toString());
                }

                addCamera(camName, camStatus, camNotifications, camUrl, camStartTime, camEndTime);
                getLatestCamera().setLog(camLog);
                dataLoaded = true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();

            // Load mock data if there is a problem loading data
            addCamera("Example Camera", 1, true,  "https://ia800309.us.archive.org/2/items/Popeye_Nearlyweds/Popeye_Nearlyweds_512kb.mp4", "4:45", "5:45");
            cameras.get(0).addLogEntry("Camera created.");

            addCamera("Test Camera", 0, false, "https://ia600208.us.archive.org/4/items/Popeye_forPresident/Popeye_forPresident_512kb.mp4", "10:20", "14:20");
            cameras.get(1).addLogEntry("Camera created.");

            addCamera("Disabled Camera", 2, true, "https://ia902606.us.archive.org/15/items/Popeye_Cooking_With_Gags_1954/Popeye_Cookin_with_Gags_512kb.mp4", "21:38", "23:00");
            cameras.get(2).addLogEntry("Camera created.");
            cameras.get(2).addLogEntry("Camera turned off.");
            cameras.get(2).addLogEntry("Camera turned on.");
            cameras.get(2).addLogEntry("Video viewed.");
            cameras.get(2).addLogEntry("Video viewed.");
            cameras.get(2).addLogEntry("Camera disabled.");
            cameras.get(2).addLogEntry("Camera enabled.");
            cameras.get(2).addLogEntry("Camera disabled.");

            dataLoaded = true;
        }
    }

    public Camera getCurrentCamera() {
        return cameras.get(currCamIndex);
    }

    public Camera getLatestCamera() { return cameras.get(cameras.size() - 1); }

    public void editCurrentCameraSettings(String name, int status, boolean notifications, String url, String startTime, String endTime) {
        Camera curr = getCurrentCamera();
        curr.setName(name);
        curr.setStatus(status);
        curr.setNotifications(notifications);
        curr.setUrl(url);
        curr.setStartTime(startTime);
        curr.setEndTime(endTime);
    }

    public void editSettings(boolean notifications) {
        settings.setNotifications(notifications);
    }

    public void removeCameraAt(int index) {
        cameras.remove(index);
    }

    public void addCamera(String name, int status, boolean notifications, String url, String startTime, String endTime) {
        cameras.add(new Camera(name, status, notifications, url, startTime, endTime));
    }

    public void logCurrentCamera(String in) {
        getCurrentCamera().addLogEntry(in);
    }

    public String getStatusCurrentCamera() {
        return Integer.toString(getCurrentCamera().getStatus());
    }

    public String formatTime(int hour, int minute) {
        return String.format(Locale.getDefault(), "%1$d:%2$02d", hour, minute);
    }

    public void createNotification(String title, String message) {
        if (settings.getNotifications()) {
            NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.ic_camera_icon)
                    .setContentTitle(getString(R.string.cameras_activity_name) + ": " + title)
                    .setContentText(message);
            nManager.notify(001, mBuilder.build());
        }
    }

    // Getters
    public ArrayList<Camera> getCameras() { return cameras; }
    public Settings getSettings() { return settings; }
    public int getCurrCamIndex() { return currCamIndex; }

    // Setters
    public void setCurrCamIndex(int n) { currCamIndex = n; }
}

class Camera {

    private String name;
    private int status;
    private boolean notifications;
    private String url;
    private String startTime;
    private String endTime;
    private ArrayList<String> log;

    // Constructor
    public Camera(String name, int status, boolean notifications, String url, String startTime, String endTime) {
        this.name = name;
        this.status = status;
        this.notifications = notifications;
        this.url = url;
        this.startTime = startTime;
        this.endTime = endTime;
        this.log = new ArrayList<>();
    }

    // Helper methods
    public void addLogEntry(String descr) {
        DateFormat dateFormat = new SimpleDateFormat("M/dd/yy HH:mm", Locale.US);
        String newLog = dateFormat.format(new Date()) + " - " + descr;
        this.log.add(newLog);
    }

    // Getters
    public String getName() { return name; }
    public int getStatus() { return status; }
    public boolean getNotifications() { return notifications; }
    public String getUrl() { return url; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public ArrayList<String> getLog() { return log; }

    // Setters
    public void setName(String n) { name = n; }
    public void setStatus(int n) { status = n; }
    public void setNotifications(boolean n) { notifications = n; }
    public void setUrl(String n) { url = n; }
    public void setStartTime(String n) { startTime = n; }
    public void setEndTime(String n) { endTime = n; }
    public void setLog(ArrayList<String> n) { log = n; }
}

class Settings {

    private boolean notifications;

    // Constructor
    public Settings() {
        // Default settings values
        notifications = true;
    }

    // Getters
    public boolean getNotifications() { return notifications; }

    // Setters
    public void setNotifications(boolean n) { notifications = n; }
}
