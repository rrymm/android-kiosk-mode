package ru.spicedevelopers.igorpavlov.kioskmode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class DropboxVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        CircledVideoView videoView = (CircledVideoView) findViewById(R.id.videoView);
        assert videoView != null;
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DropboxVideoActivity.this, KioskActivity.class);
                startActivity(intent);
            }
        });

        videoView.UpdateVideoSource(this);
    }

}
