package com.w8udied2fast.chording.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.w8udied2fast.chording.data.model.Chord;
import com.w8udied2fast.chording.data.repository.ChordRepository;

import java.util.List;

public class ChordsViewModel extends AndroidViewModel {
    private final ChordRepository repository;
    private final MutableLiveData<List<Chord>> chordsLiveData = new MutableLiveData<>();

    public ChordsViewModel(Application application) {
        super(application);
        repository = new ChordRepository();
        repository.init(application.getApplicationContext());
        loadAllChords();
    }

    public LiveData<List<Chord>> getChordsLiveData() {
        return chordsLiveData;
    }

    public void loadAllChords() {
        chordsLiveData.postValue(repository.getAllChords());
    }

    public void search(String query) {
        chordsLiveData.postValue(repository.searchChords(query));
    }

    public void filterByRoot(String root) {
        chordsLiveData.postValue(repository.getChordsByRoot(root));
    }

    public boolean isFavorite(String name) {
        return repository.isFavorite(name);
    }

    public void toggleFavorite(String name) {
        repository.toggleFavorite(name);
        // После подключения Room здесь будет обновление LiveData
    }
}