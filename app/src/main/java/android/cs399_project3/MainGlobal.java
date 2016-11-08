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
        addCamera("test0", 1);
        addCamera("test1", 0);
        addCamera("test2", 0);
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

    public void addCamera(String name, int status) {
        cameras.add(new Camera(name, status));
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
    private CameraSettings settings;

    // Constructor
    public Camera(String name, int status) {
        this.name = name;
        this.status = status;
        settings = new CameraSettings();
    }

    // Getters
    public String getName() { return name; }
    public int getStatus() { return status; }

    // Setters
    public void setName(String n) { name = n; }
    public void setStatus(int n) { status = n; }
}

class Settings {

    private boolean notifications;

    // Constructor
    public Settings() {
        // Default settings values
        notifications = true;
    }
}

class CameraSettings {

    boolean notifications;
    boolean saveImages;

    // Constructor
    public CameraSettings() {
        // Default camera setting values
        notifications = true;
        saveImages = true;
    }
}