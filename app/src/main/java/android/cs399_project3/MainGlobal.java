package android.cs399_project3;

import android.app.Application;

import java.util.ArrayList;


public class MainGlobal extends Application {

    private ArrayList<Camera> cameras;
    private Settings settings;
    private int currCamIndex = -1;

    // Constructor
    public MainGlobal() {
        this.cameras = new ArrayList<Camera>();
        this.settings = new Settings();

        // Mock data
        addCamera("test0", 1, "https://ia800309.us.archive.org/2/items/Popeye_Nearlyweds/Popeye_Nearlyweds_512kb.mp4");
        addCamera("test1", 0, "https://ia600208.us.archive.org/4/items/Popeye_forPresident/Popeye_forPresident_512kb.mp4");
        addCamera("test2", 0, "https://ia902606.us.archive.org/15/items/Popeye_Cooking_With_Gags_1954/Popeye_Cookin_with_Gags_512kb.mp4");
    }

    // Helper methods
    public Camera getCurrentCamera() {
        return cameras.get(currCamIndex);
    }

    public void editCurrentCameraSettings(String name, int status) {
        Camera curr = getCurrentCamera();
        curr.setName(name);
        curr.setStatus(status);
    }

    public void removeCameraAt(int index) {
        cameras.remove(index);
    }

    public void addCamera(String name, int status, String url) {
        cameras.add(new Camera(name, status, url));
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

    // Constructor
    public Camera(String name, int status, String url) {
        this.name = name;
        this.status = status;
        this.url = url;
    }

    // Getters
    public String getName() { return name; }
    public int getStatus() { return status; }
    public String getUrl() { return url; }

    // Setters
    public void setName(String n) { name = n; }
    public void setStatus(int n) { status = n; }
    public void setUrl(String n) { url = n; }
}

class Settings {

    private boolean notifications;

    // Constructor
    public Settings() {
        // Default settings values
        notifications = true;
    }
}
