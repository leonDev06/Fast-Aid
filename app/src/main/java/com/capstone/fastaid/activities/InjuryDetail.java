package com.capstone.fastaid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.capstone.fastaid.MyApp;
import com.capstone.fastaid.R;
import com.capstone.fastaid.models.Injury;

public class InjuryDetail extends AppCompatActivity {
    private TextView mInjuryName, mInjuryDescription, mInjuryFirstAid;

    private Injury injury;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injury_detail);

        mInjuryName = findViewById(R.id.mInjuryNameDetail);
        mInjuryDescription = findViewById(R.id.injuryDescription);
        mInjuryFirstAid = findViewById(R.id.injuryFirstAid);

        String injuryName = getIntent().getStringExtra("NAME");
        this.injury = ((MyApp) getApplication()).getInjuries().get(injuryName);

        setupUiDisplay();
    }

    private void setupUiDisplay(){
        mInjuryName.setText(injury.name);
        mInjuryDescription.setText(injury.description);
        mInjuryFirstAid.setText(injury.firstAid);
    }
}