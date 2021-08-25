package de.prog3.ackerschlagkartei.data.models;

import java.util.Date;

public class DocumentModel {
    private String contentType;
    private String url;
    private String uri;
    private Date uploadDate;

    public DocumentModel() {

    }

    public String getContentType() {
        return contentType;
    }

    public String getUrl() {
        return url;
    }

    public String getUri() {
        return uri;
    }

    public Date getUploadDate() {
        return uploadDate;
    }
}
