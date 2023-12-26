package com.capstone.fastaid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import com.capstone.fastaid.MyApp;
import com.capstone.fastaid.R;
import com.capstone.fastaid.adapters.InjuriesListAdapter;
import com.capstone.fastaid.models.Injury;

import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private String[] injuries;

    private RecyclerView injuriesList;
    private Button btnCall;

    private final Uri EMERGENCY_NUMBER = Uri.parse("tel:911");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        setupInjuriesList();

        // Clickable buttons
        btnCall = findViewById(R.id.call_button);
        btnCall.setOnClickListener(listener -> makeEmergencyCall());
    }

    private void bindViews(){
        injuriesList = findViewById(R.id.injuriesList);
    }

    private void setupInjuriesList(){
        // Get loaded injuries' names
        HashMap<String, Injury> loadedInjuries = ((MyApp) getApplication()).getInjuries();
        injuries = Arrays.stream(loadedInjuries.keySet().toArray())
                .toArray(String[]::new);

        // Feed into injuries list adapter
        InjuriesListAdapter adapter = new InjuriesListAdapter(injuries, this);
        injuriesList.setAdapter(adapter);
        injuriesList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void makeEmergencyCall(){
        Intent dial = new Intent(Intent.ACTION_DIAL);
        dial.setData(EMERGENCY_NUMBER);
        startActivity(dial);
    }
}