package android.cs399_project3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class CameraActivity extends AppCompatActivity {

    private MainGlobal mainGlobal;

    private NotificationManager mNotifyMgr;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mainGlobal = ((MainGlobal) this.getApplication()); // Get global data
        mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Change activity name to name of current camera
        getSupportActionBar().setTitle(mainGlobal.getCurrentCamera().getName());

        //  Add array adapter to camera log ListView
        ListView logList = (ListView) findViewById(R.id.camera_log);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mainGlobal.getCurrentCamera().getLog());
        logList.setAdapter(mAdapter);

        // Add url to video view
        final VideoView videoView = (VideoView) findViewById(R.id.video_view);
        Uri vidUri = Uri.parse(mainGlobal.getCurrentCamera().getUrl());
        videoView.setVideoURI(vidUri);

        // Apply video controls to video view
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Progress dialog for loading video
        final ProgressBar spinner = (ProgressBar) findViewById(R.id.video_loading);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // Log successful load
                mainGlobal.logCurrentCamera("Video viewed.");
                mAdapter.notifyDataSetChanged();

                // Test notifications
                android.support.v4.app.NotificationCompat.Builder mBuilder =
                        new android.support.v4.app.NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.ic_camera_icon)
                                .setContentTitle(getString(R.string.cameras_activity_name) + ": Video loaded!")
                                .setContentText("Your video is now ready to go.");
                mNotifyMgr.notify(001, mBuilder.build());

                spinner.setVisibility(View.GONE);
                videoView.start();
            }
        });

        // Catch error loading video
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // Log error
                mainGlobal.logCurrentCamera("Error loading.");
                mAdapter.notifyDataSetChanged();

                spinner.setVisibility(View.GONE);
                return false;
            }
        });

        // Check if the user is landscape on activity start
        orientationChanges(getResources().getConfiguration().orientation);


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        orientationChanges(newConfig.orientation);
        super.onConfigurationChanged(newConfig);
    }

    // Helper method used to make the orientation changes to layout
    private void orientationChanges(int orientation) {
        // Get video view container and create new layout params
        FrameLayout videoViewContainer = (FrameLayout) findViewById(R.id.video_view_container);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        // If going to landscape
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            lp.weight = 0;
            getSupportActionBar().hide();
        }
        // If going to portrait
        else {
            lp.weight = 4;
            getSupportActionBar().show();
        }

        // Set layout params and do the normal config changes
        videoViewContainer.setLayoutParams(lp);
    }
}
