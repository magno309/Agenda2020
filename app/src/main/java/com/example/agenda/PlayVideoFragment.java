package com.example.agenda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlayVideoFragment extends Fragment {
    public static final String ARG_RUTA_VIDEO = "ruta_video";
    MediaController mediaController;
    VideoView  videoView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.play_video_view, container, false);
        this.videoView = (VideoView) view.findViewById(R.id.videoView);
        this.mediaController = new MediaController(getActivity());
        this.mediaController.setMediaPlayer(this.videoView);
        this.mediaController.setAnchorView(this.videoView);
        this.videoView.setMediaController(this.mediaController);
        Bundle args = getArguments();
        if (args != null) {
            this.videoView.setVideoPath(args.getString(ARG_RUTA_VIDEO));
            this.videoView.start();
        }
        return view;
    }
}
