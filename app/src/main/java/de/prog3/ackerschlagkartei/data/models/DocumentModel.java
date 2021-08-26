package de.prog3.ackerschlagkartei.data.models;

import java.util.Date;

public class DocumentModel {
    private String contentType;
    private String uriThumbnail;
    private String uriFullsize;
    private Date uploadDate;

    public DocumentModel() { }

    public String getContentType() {
        return contentType;
    }

    public String getUriThumbnail() {
        return uriThumbnail;
    }

    public String getUriFullsize() {
        return uriFullsize;
    }

    public Date getUploadDate() {
        return uploadDate;
    }
}
