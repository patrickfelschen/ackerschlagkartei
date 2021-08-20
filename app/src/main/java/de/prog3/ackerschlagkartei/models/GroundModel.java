package de.prog3.ackerschlagkartei.models;

public class GroundModel {
    private String bkr;
    private String date;
    private String groundType;
    private String humus;
    private float magnesium;
    private float phValue;
    private float phosphorus;
    private float potassium;

    public GroundModel() {
    }

    public GroundModel(String bkr, String date, String groundType, String humus, float magnesium, float phValue, float phosphorus, float potassium) {
        this.bkr = bkr;
        this.groundType = groundType;
        this.humus = humus;
        this.magnesium = magnesium;
        this.phValue = phValue;
        this.phosphorus = phosphorus;
        this.potassium = potassium;
        this.date = date;
    }

    public String getBkr() {
        return bkr;
    }

    public String getGroundType() {
        return groundType;
    }

    public String getHumus() {
        return humus;
    }

    public float getMagnesium() {
        return magnesium;
    }

    public float getPhValue() {
        return phValue;
    }

    public float getPhosphorus() {
        return phosphorus;
    }

    public float getPotassium() {
        return potassium;
    }

    public String getDate() { return date; }
}
