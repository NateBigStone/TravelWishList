package com.nathan.travelwishlist;

import java.text.DateFormat;
import java.util.Date;

public class Place {

    private String name;
    private Date dateCreated;
    private String reason;

    Place(String name, String reason) {
        this.name = name;
        this.dateCreated = new Date();
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public String getDateCreated() {
        return DateFormat.getDateInstance().format(dateCreated);
    }

    public String getReason(){
        return reason;
    }
}
