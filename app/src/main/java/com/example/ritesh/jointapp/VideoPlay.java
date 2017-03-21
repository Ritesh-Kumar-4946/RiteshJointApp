package com.example.ritesh.jointapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by ritesh on 14/8/16.
 */
public class VideoPlay extends AppCompatActivity {

    VideoView videoView;
    MediaController mediaController;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoplay);
    }
}
