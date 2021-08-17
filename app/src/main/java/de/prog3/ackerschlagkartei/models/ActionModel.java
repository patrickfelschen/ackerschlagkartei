package de.prog3.ackerschlagkartei.models;

import java.util.Date;

public class ActionModel {
    private String description;
    private Date date;

    public ActionModel() {

    }

    public ActionModel(String description, Date date) {
        this.description = description;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

}
