package com.capstone.fastaid;

import android.app.Application;

import com.capstone.fastaid.models.Injury;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class MyApp extends Application {

    private HashMap<String, Injury> injuries;

    @Override
    public void onCreate() {
        super.onCreate();

        // Load injuries from JSON file. If JSON file not found, load from HARDCODED instead.
        try{
            injuries = loadInjuriesFromFile();
        }catch (IOException e){
            injuries = loadHardcodedInjuries();
        }
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

        return parseToHashMap(buffer);
    }

    private HashMap<String, Injury> loadInjuriesFromFile() throws IOException {
        // Loads injuries from JSON file accessible to public in Android Files of this app
        File injuriesFile = new File(getExternalFilesDir(null), "injuries.json");
        FileInputStream fileInputStream = new FileInputStream(injuriesFile);

        // Read the contents of the injuriesFile
        byte[] buffer = new byte[(int)injuriesFile.length()];
        fileInputStream.read(buffer);
        fileInputStream.close();

        return parseToHashMap(buffer);
    }

    private HashMap<String, Injury> parseToHashMap(byte[] buffer){
        // Helper. Converts Array to Hashmap<String, Injury>
        HashMap<String, Injury> map = new HashMap<>();

        String json = new String(buffer, StandardCharsets.UTF_8);
        Injury[] arr = new Gson().fromJson(json, Injury[].class);

        for (Injury injury : arr){
            map.put(injury.name, injury);
        }

        return map;
    }
}
