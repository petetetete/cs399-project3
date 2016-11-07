package android.cs399_project3;

import android.app.Application;

import java.util.ArrayList;


public class MainGlobal extends Application {

    private ArrayList<Camera> cameras;
    private Settings settings;

    // Constructor
    public MainGlobal() {
        this.cameras = new ArrayList<Camera>();
        this.settings = new Settings();

        // Mock data
        this.addCamera("test0", 1);
        this.addCamera("test1", 0);
        this.addCamera("test2", 0);
    }

    // Helper methods
    public Camera getCameraAt(int index) {
        return this.cameras.get(index);
    }

    public void addCamera(String name, int status) {
        this.cameras.add(new Camera(name, status));
    }

    // Getters
    public ArrayList<Camera> getCameras() { return this.cameras; }
    public Settings getSettings() { return this.settings; }
}

class Camera {

    private String name;
    private int status;
    private CameraSettings settings;

    // Constructor
    public Camera(String name, int status) {
        this.name = name;
        this.status = status;
        this.settings = new CameraSettings();
    }

    // Getters
    public String getName() { return this.name; }
    public int getStatus() { return this.status; }

    // Setters
    public void setName(String n) { this.name = n; }
    public void setStatus(int n) { this.status = n; }
}

class Settings {

    private boolean notifications;

    // Constructor
    public Settings() {
        // Default settings values
        this.notifications = true;
    }
}

class CameraSettings {

    boolean notifications;
    boolean saveImages;

    // Constructor
    public CameraSettings() {
        // Default camera setting values
        this.notifications = true;
        this.saveImages = true;
    }
}