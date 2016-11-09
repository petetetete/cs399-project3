package android.cs399_project3;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class CameraActivity extends AppCompatActivity {

    private MainGlobal mainGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mainGlobal = ((MainGlobal) this.getApplication()); // Get global data

        // Change activity name to name of current camera
        getSupportActionBar().setTitle(mainGlobal.getCurrentCamera().getName());

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
                spinner.setVisibility(View.GONE);
                videoView.start();
            }
        });

        // Catch error loading video
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                spinner.setVisibility(View.GONE);
                return false;
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        // Get video view container and create new layout params
        FrameLayout videoViewContainer = (FrameLayout) findViewById(R.id.video_view_container);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        // If going to landscape
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
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
        super.onConfigurationChanged(newConfig);
    }
}
