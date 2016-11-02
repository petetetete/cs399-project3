package android.cs399_project3;

import android.app.Application;


public class MainGlobal extends Application {

    private Camera[] cameras;
    private Settings settings;

    public MainGlobal() {
        this.settings = new Settings();
    }

    // Helper methods
    public Camera getCameraAt(int index) {
        return this.cameras[index];
    }

    // Getters
    public Camera[] getCameras() { return this.cameras; }
}

class Camera {

    private String name;
    private int status;
    private CameraSettings settings;

    public Camera(String name) {
        this.name = name;
        this.status = 0;
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

    boolean notifications;

    public Settings() {
        // Default settings values
        this.notifications = true;
    }
}

class CameraSettings {

    boolean notifications;
    boolean saveImages;

    public CameraSettings() {
        // Default camera setting values
        this.notifications = true;
        this.saveImages = true;
    }
}