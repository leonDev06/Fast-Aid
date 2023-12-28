package com.capstone.fastaid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.capstone.fastaid.MyApp;
import com.capstone.fastaid.R;
import com.capstone.fastaid.adapters.InjuriesListAdapter;
import com.capstone.fastaid.models.Injury;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private String[] injuries;

    private RecyclerView injuriesList;
    private EditText mSearchBar;
    private Button btnCall;
    private ImageButton btnSearch;

    private InjuriesListAdapter adapter;

    private final Uri EMERGENCY_NUMBER = Uri.parse("tel:911");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        setupInjuriesList();
        observeSearchBar();

        // Clickable buttons
        btnCall = findViewById(R.id.call_button);
        btnCall.setOnClickListener(listener -> makeEmergencyCall());

        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(listener -> {
            search();
        });
    }

    private void bindViews(){
        injuriesList = findViewById(R.id.injuriesList);
        mSearchBar = findViewById(R.id.searchBar);
    }

    private void setupInjuriesList(){
        // Get loaded injuries' names
        loadAllInjuries();

        // Feed into injuries list adapter
        adapter = new InjuriesListAdapter(injuries, this);
        injuriesList.setAdapter(adapter);
        injuriesList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void makeEmergencyCall(){
        Intent dial = new Intent(Intent.ACTION_DIAL);
        dial.setData(EMERGENCY_NUMBER);
        startActivity(dial);
    }

    private void search(){
        String keyword = mSearchBar.getText().toString().toLowerCase();
        hideKeyboard();

        loadAllInjuries();

        // Search for all relevant results
        ArrayList<String> relevantInjuries = new ArrayList<String>();
        for (String injury : injuries){
            if(injury.toLowerCase().contains(keyword)){
                relevantInjuries.add(injury);
            }
        }

        // Feed search results to adapter to display new list
        injuries = Arrays.stream(relevantInjuries.toArray()).toArray(String[]::new);
        adapter.setInjuriesList(injuries);
        injuriesList.setAdapter(adapter);
    }

    private void loadAllInjuries(){
        HashMap<String, Injury> loadedInjuries = ((MyApp) getApplication()).getInjuries();
        injuries = Arrays.stream(loadedInjuries.keySet().toArray())
                .toArray(String[]::new);
    }

    private void observeSearchBar(){
        mSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    loadAllInjuries();
                    adapter.setInjuriesList(injuries);
                    injuriesList.setAdapter(adapter);
                }
            }
        });
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchBar.getWindowToken(), 0);
    }
}