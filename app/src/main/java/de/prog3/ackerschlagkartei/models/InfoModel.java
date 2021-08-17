package de.prog3.ackerschlagkartei.models;

import com.google.firebase.firestore.GeoPoint;

public class InfoModel {
    private float area;
    private String description;
    private boolean phosphateSensitiveArea;
    private boolean redArea;
    private boolean waterProtectionArea;
    private GeoPoint[] positions;

    public InfoModel() {
    }

    public InfoModel(float area, String description, boolean phosphateSensitiveArea, boolean redArea, boolean waterProtectionArea, GeoPoint[] positions) {
        this.area = area;
        this.description = description;
        this.phosphateSensitiveArea = phosphateSensitiveArea;
        this.redArea = redArea;
        this.waterProtectionArea = waterProtectionArea;
        this.positions = positions;
    }

    public float getArea() {
        return area;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPhosphateSensitiveArea() {
        return phosphateSensitiveArea;
    }

    public boolean isRedArea() {
        return redArea;
    }

    public boolean isWaterProtectionArea() {
        return waterProtectionArea;
    }

    public GeoPoint[] getPositions() {
        return positions;
    }
}
