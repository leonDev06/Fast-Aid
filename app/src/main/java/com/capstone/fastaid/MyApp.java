package com.capstone.fastaid;

import android.app.Application;

import com.capstone.fastaid.models.Injury;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class MyApp extends Application {

    private HashMap<String, Injury> injuries;

    @Override
    public void onCreate() {
        super.onCreate();

        // Load injuries from JSON file.
        // Is alive for the whole app lifecycle. Shouldn't hog too much memory as it's relatively few.
        injuries = loadHardcodedInjuries();
    }

    // GETTERS
    public HashMap<String, Injury> getInjuries() {
        return injuries;
    }

    // PRIVATE METHODS
    private HashMap<String, Injury> loadHardcodedInjuries(){
        // Loads injuries from JSON file hardcoded upon build of this app.
        byte[] buffer;

        try {
            InputStream inputStream = getAssets().open("json/injuries.json");

            // Read the contents of the static resource file
            buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String json = new String(buffer, StandardCharsets.UTF_8);
        return parseToHashMap(json);
    }

    private HashMap<String, Injury> parseToHashMap(String json){
        // Helper. Converts Array to Hashmap<String, Injury>
        HashMap<String, Injury> map = new HashMap<>();

        Injury[] arr = new Gson().fromJson(json, Injury[].class);

        for (Injury injury : arr){
            map.put(injury.name, injury);
        }

        return map;
    }
}
