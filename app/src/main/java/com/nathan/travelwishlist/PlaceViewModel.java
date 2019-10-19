package com.nathan.travelwishlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PlaceViewModel extends AndroidViewModel {

    private PlaceRepository repository;

    private LiveData<List<Place>> allRecords;

    private LiveData<Integer> rowCount;

    public PlaceViewModel(@NonNull Application application) {
        super(application);
        repository = new PlaceRepository(application);
        allRecords = repository.getAllRecords();
        rowCount = repository.getRowCount();
    }

    public LiveData<List<Place>> getAllRecords() {
        return allRecords;
    }

    public LiveData<Place> getRecordForName(String name) {
        return repository.getRecordForName(name);
    }

    public LiveData<Integer> getRowCount(){
        return rowCount;
    }

    public void insert(Place record) {
        repository.insert(record);
    }

    public void update(Place record) {
        repository.update(record);
    }
}
