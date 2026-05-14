package com.w8udied2fast.chording.data.repository;

import android.content.Context;

import com.w8udied2fast.chording.data.model.Chord;
import com.w8udied2fast.chording.data.source.JsonChordSource;

import java.util.ArrayList;
import java.util.List;

public class ChordRepository {
    private final JsonChordSource jsonSource = new JsonChordSource();
    private final List<Chord> allChords = new ArrayList<>();
    private boolean isInitialized = false;

    public void init(Context context) {
        if (isInitialized) return;
        allChords.addAll(jsonSource.loadChords(context));
        isInitialized = true;
    }

    public List<Chord> getAllChords() {
        return new ArrayList<>(allChords); // защита от внешних изменений
    }

    public List<Chord> searchChords(String query) {
        if (query == null || query.trim().isEmpty()) return getAllChords();
        String q = query.trim().toLowerCase();
        List<Chord> result = new ArrayList<>();
        for (Chord c : allChords) {
            if (c.getName().toLowerCase().contains(q)) {
                result.add(c);
            }
        }
        return result;
    }

    public List<Chord> getChordsByRoot(String root) {
        List<Chord> result = new ArrayList<>();
        for (Chord c : allChords) {
            if (c.getRoot().equalsIgnoreCase(root)) {
                result.add(c);
            }
        }
        return result;
    }

    // Заглушки для избранного
    public boolean isFavorite(String chordName) { return false; }
    public void toggleFavorite(String chordName) {}
}