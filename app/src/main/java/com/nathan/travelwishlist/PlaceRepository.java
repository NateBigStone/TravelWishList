package com.nathan.travelwishlist;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PlaceRepository {

    private TravelDAO travelDAO;

    public PlaceRepository(Application application) {
        PlaceDatabase db = PlaceDatabase.getDatabase(application);
        travelDAO = db.travelDAO();
    }

    public void insert(Place record) {
        new InsertPlaceAsync(travelDAO).execute(record);
    }

    static class InsertPlaceAsync extends AsyncTask<Place, Void, Void> {

        private TravelDAO travelDAO;

        InsertPlaceAsync(TravelDAO travelDAO) {
            this.travelDAO = travelDAO;
        }

        @Override
        protected Void doInBackground(Place... places) {
            travelDAO.insert(places);
            return null;
        }

    }

    public void update(Place record) {

        new UpdatePlaceAsync(travelDAO).execute(record);

    }

    static class UpdatePlaceAsync extends AsyncTask<Place, Void, Void> {

        private TravelDAO travelDAO;

        UpdatePlaceAsync(TravelDAO travelDAO) {
            this.travelDAO = travelDAO;
        }

        @Override
        protected Void doInBackground(Place... places) {
            travelDAO.update(places);
            return null;
        }

    }

    public LiveData<List<Place>> getAllRecords() {
        return travelDAO.getAllRecords();
    }

    public LiveData<Place> getRecordForName(String name) {
        return travelDAO.getRecordForName(name);
    }
    public LiveData<Integer> getRowCount() {
        return travelDAO.getRowCount();
    }
}
