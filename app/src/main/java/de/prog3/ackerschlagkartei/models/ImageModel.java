package de.prog3.ackerschlagkartei.models;

import java.util.Date;

public class ImageModel {
    private String description;
    private String url;
    private Date date;

    public ImageModel() {

    }

    public ImageModel(String description, String url, Date date) {
        this.description = description;
        this.url = url;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public Date getDate() {
        return date;
    }
}
