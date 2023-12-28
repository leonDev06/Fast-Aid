package com.capstone.fastaid.activities;

import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.capstone.fastaid.MyApp;


import com.capstone.fastaid.R;
import com.capstone.fastaid.models.Injury;

public class InjuryDetail extends AppCompatActivity {
    private TextView mInjuryName, mInjuryDescription, mInjuryFirstAid;
    private VideoView mVideo;
    private ImageView mThumbnail;
    private ScrollView mScrollView;
    private FrameLayout mFrame;

    private Injury injury;

    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injury_detail);

        bindViews();
        getInjuryDetail();
        setupUiDisplay();
        setupVideo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mThumbnail.setVisibility(View.VISIBLE);

    }

    private void getInjuryDetail() {
        String injuryName = getIntent().getStringExtra("NAME");
        this.injury = ((MyApp) getApplication()).getInjuries().get(injuryName);
    }

    private void bindViews(){
        // TextViews
        mInjuryName = findViewById(R.id.mInjuryNameDetail);
        mInjuryDescription = findViewById(R.id.injuryDescription);
        mInjuryFirstAid = findViewById(R.id.injuryFirstAid);
        // VideoView
        mVideo = findViewById(R.id.injuryVideo);
        // ScrollView
        mScrollView = findViewById(R.id.injuryScroll);
        // ImageView
        mThumbnail = findViewById(R.id.thumbnail);
        // Frame
        mFrame = findViewById(R.id.frameLayout);
    }
    private void setupUiDisplay(){
        mInjuryName.setText(injury.name);
        mInjuryDescription.setText(injury.description);
        mInjuryFirstAid.setText(injury.firstAid);
    }

    private void setupVideo(){
        // Load video to play
        int resId = getResources().getIdentifier(injury.videoName, "raw", getPackageName());
        Uri path = Uri.parse("android.resource://"+getPackageName() + "/"+ resId);
        mVideo.setVideoURI(path);

        setupMediaController();

        // If video exists, set thumbnail. Else, don't render videoView
        // Starts video on thumbnail click
        if(resId != 0) setupVideoThumbnail(path);
        else mFrame.setVisibility(View.GONE);
    }

    private void setupMediaController(){
        // Add media controller
        mediaController = new MediaController(this);
        mVideo.setMediaController(mediaController);
        mediaController.setAnchorView(mVideo);

        // Hide Media Controller onScroll
        mScrollView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            mediaController.hide();
        });

        // Avoid mediaController preventing back
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mediaController.addOnUnhandledKeyEventListener((v, event) -> {
                //Handle BACK button
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
                {
                    mediaController.hide();
                    finish();
                }
                return true;
            });
        }
    }

    private void setupVideoThumbnail(Uri path){
        // Get a thumbnail from video
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(this, path);

        // Set video thumbnail
        Bitmap thumbnail = retriever.getFrameAtTime();
        mThumbnail.setImageBitmap(thumbnail);

        // Destroy thumbnail onClick and start video
        mThumbnail.setOnClickListener(listener -> {
            if(mThumbnail.getVisibility() == View.VISIBLE){
                mThumbnail.setVisibility(View.GONE);
                mVideo.start();
                mediaController.hide();
            }
        });
    }
}