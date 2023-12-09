package com.capstone.fastaid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.capstone.fastaid.MyApp;
import com.capstone.fastaid.R;
import com.capstone.fastaid.adapters.InjuriesListAdapter;
import com.capstone.fastaid.models.Injury;

import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private String[] injuries;

    private RecyclerView injuriesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind Views
        injuriesList = findViewById(R.id.injuriesList);

        // Get list of injuries to display
        setupInjuriesList();
    }

    private void setupInjuriesList(){
        // Get loaded injuries' names
        HashMap<String, Injury> loadedInjuries = ((MyApp) getApplication()).getInjuries();
        injuries = Arrays.stream(loadedInjuries.keySet().toArray())
                // .map(injury -> injury)
                .toArray(String[]::new);

        // Feed into injuries list adapter
        InjuriesListAdapter adapter = new InjuriesListAdapter(injuries, this);
        injuriesList.setAdapter(adapter);
        injuriesList.setLayoutManager(new LinearLayoutManager(this));
    }
}