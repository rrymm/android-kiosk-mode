package ru.spicedevelopers.igorpavlov.kioskmode;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by igorpavlov on 22.07.16.
 */
public class CircledVideoView extends VideoView {

    public int currentVideoId = 0;

    public CircledVideoView(Context context) {
        super(context);
    }

    public void UpdateVideoSource(final Activity activity) {
        final ArrayList<File> videoFiles = DropboxService.getInstance().VideoFiles();
        if (videoFiles.size() > 0) {
            String videoSource = videoFiles.get(currentVideoId).getAbsolutePath();

            setVideoPath(videoSource);
            setMediaController(new MediaController(activity));
            requestFocus(0);

            setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    currentVideoId++;
                    if (videoFiles.size() > currentVideoId) {
                        currentVideoId = 0;
                    }

                    UpdateVideoSource(activity);
                }
            });

            start();
        } else {
            Toast.makeText(activity.getApplicationContext(),
                    "No videos",
                    Toast.LENGTH_LONG).show();
        }
    }


}
