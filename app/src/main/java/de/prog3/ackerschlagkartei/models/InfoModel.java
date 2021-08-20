package de.prog3.ackerschlagkartei.models;

import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public class InfoModel {
    private double area;
    private String description;
    private boolean phosphateSensitiveArea;
    private boolean redArea;
    private boolean waterProtectionArea;
    private List<GeoPoint> positions;

    public InfoModel() {
    }

    public InfoModel(float area, String description, boolean phosphateSensitiveArea, boolean redArea, boolean waterProtectionArea, List<GeoPoint> positions) {
        this.area = area;
        this.description = description;
        this.phosphateSensitiveArea = phosphateSensitiveArea;
        this.redArea = redArea;
        this.waterProtectionArea = waterProtectionArea;
        this.positions = positions;
    }

    public double getArea() {
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

    public List<GeoPoint> getPositions() {
        return positions;
    }
}
