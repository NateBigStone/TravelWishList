package com.nathan.travelwishlist;

import java.text.DateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Place {

    @PrimaryKey
    @NonNull

    private String name;

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    private String dateCreated;
    private String reason;

    Place(@NonNull String name, String reason) {
        this.name = name;
        this.dateCreated = DateFormat.getDateInstance().format(new Date());
        this.reason = reason;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getReason(){
        return reason;
    }
}
