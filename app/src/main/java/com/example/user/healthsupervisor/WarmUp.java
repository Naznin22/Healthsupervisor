package com.example.user.healthsupervisor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

public class WarmUp extends AppCompatActivity {

    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warm_up);
        videoView = (VideoView) findViewById(R.id.videoView);
    }

    public void videoplay(View v){
        String videopath = "android.resource://com.example.user.healthsupervisor"+R.raw.cloud;
    }
}
