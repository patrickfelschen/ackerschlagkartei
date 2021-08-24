package de.prog3.ackerschlagkartei.models;

import java.util.Date;

public class DocumentModel {
    private String description;
    private String url;
    private Date date;

    public DocumentModel() {

    }

    public DocumentModel(String description, String url, Date date) {
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
