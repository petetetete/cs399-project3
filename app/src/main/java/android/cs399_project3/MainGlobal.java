package android.cs399_project3;

import android.app.Application;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;


public class MainGlobal extends Application {

    private ArrayList<Camera> cameras;
    private Settings settings;
    private int currCamIndex = -1;

    // Constructor
    public MainGlobal() {
        this.cameras = new ArrayList<Camera>();
        this.settings = new Settings();

        // Mock data
        addCamera("Example Camera", 1, "https://ia800309.us.archive.org/2/items/Popeye_Nearlyweds/Popeye_Nearlyweds_512kb.mp4", "4:45", "5:45");
        addCamera("Test Camera", 0, "https://ia600208.us.archive.org/4/items/Popeye_forPresident/Popeye_forPresident_512kb.mp4", "10:20", "14:20");
        addCamera("Disabled Camera", -1, "https://ia902606.us.archive.org/15/items/Popeye_Cooking_With_Gags_1954/Popeye_Cookin_with_Gags_512kb.mp4", "21:38", "23:00");
    }

    // Helper methods
    public Camera getCurrentCamera() {
        return cameras.get(currCamIndex);
    }

    public void editCurrentCameraSettings(String name, int status, String url, String startTime, String endTime) {
        Camera curr = getCurrentCamera();
        curr.setName(name);
        curr.setStatus(status);
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

    public void addCamera(String name, int status, String url, String startTime, String endTime) {
        cameras.add(new Camera(name, status, url, startTime, endTime));
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
        if (settings.notificationsEnabled()) {
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
    private String url;
    private String startTime;
    private String endTime;
    private ArrayList<String> log;

    // Constructor
    public Camera(String name, int status, String url, String startTime, String endTime) {
        this.name = name;
        this.status = status;
        this.url = url;
        this.startTime = startTime;
        this.endTime = endTime;
        this.log = new ArrayList<>();
        addLogEntry("Camera created.");
    }

    // Helper methods
    public void addLogEntry(String descr) {
        DateFormat dateFormat = new SimpleDateFormat("M/dd/yy HH:mm");
        String newLog = dateFormat.format(new Date()) + " - " + descr;
        this.log.add(newLog);
    }

    // Getters
    public String getName() { return name; }
    public int getStatus() { return status; }
    public String getUrl() { return url; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public ArrayList<String> getLog() { return log; }

    // Setters
    public void setName(String n) { name = n; }
    public void setStatus(int n) { status = n; }
    public void setUrl(String n) { url = n; }
    public void setStartTime(String n) { startTime = n; }
    public void setEndTime(String n) { endTime = n; }
}

class Settings {

    private boolean notifications;

    // Constructor
    public Settings() {
        // Default settings values
        notifications = true;
    }

    // Getters
    public boolean notificationsEnabled() { return notifications; }

    // Setters
    public void setNotifications(boolean n) { notifications = n; }
}
