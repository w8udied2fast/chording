package com.w8udied2fast.chording.data.source;

import android.content.Context;

import com.w8udied2fast.chording.data.model.Chord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonChordSource {
    private static final String FILE_NAME = "chords.json";

    public List<Chord> loadChords(Context context) {
        List<Chord> chords = new ArrayList<>();
        try (InputStream is = context.getAssets().open(FILE_NAME)) {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray array = new JSONArray(json);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Chord chord = new Chord();
                chord.setName(obj.getString("name"));
                chord.setRoot(obj.getString("root"));

                List<Chord.Position> positions = new ArrayList<>();
                JSONArray posArray = obj.getJSONArray("positions");
                for (int j = 0; j < posArray.length(); j++) {
                    JSONObject posObj = posArray.getJSONObject(j);
                    Chord.Position pos = new Chord.Position();
                    pos.setString(posObj.getInt("string"));
                    pos.setFret(posObj.getInt("fret"));
                    positions.add(pos);
                }
                chord.setPositions(positions);
                chords.add(chord);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return chords;
    }
}