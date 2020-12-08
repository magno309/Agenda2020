package com.example.agenda;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PlayVideo extends AppCompatActivity {
    private MediaController mediaController;
    private VideoView videoView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_video_view);
        this.videoView = (VideoView) findViewById(R.id.videoView);
        this.mediaController = new MediaController(this);
        this.mediaController.setMediaPlayer(this.videoView);
        this.mediaController.setAnchorView(this.videoView);
        this.videoView.setMediaController(this.mediaController);
        String ruta = getIntent().getStringExtra("rutaVideo");
        this.videoView.setVideoPath(ruta);
        this.videoView.start();
    }
}
