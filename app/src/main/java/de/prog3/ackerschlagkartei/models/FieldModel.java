package de.prog3.ackerschlagkartei.models;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class FieldModel {
    private String uid;
    private CultivationModel cultivation;
    private GroundModel ground;
    private InfoModel info;
    private List<AccountModel> actions;
    private List<ImageModel> images;

    public FieldModel() {
        // Firestore
    }

    public FieldModel(String description, List<GeoPoint> positions) {
        this.uid = "";
        this.cultivation = new CultivationModel("", "", "", "", "");
        this.ground = new GroundModel("", "", "", 0, 0, 0, 0);
        this.info = new InfoModel(0, description, false, false, false, positions);
        this.actions = new ArrayList<>();
        this.images = new ArrayList<>();
    }

    public String getUid() {
        return uid;
    }

    public CultivationModel getCultivation() {
        return cultivation;
    }

    public GroundModel getGround() {
        return ground;
    }

    public InfoModel getInfo() {
        return info;
    }

    public List<AccountModel> getActions() {
        return actions;
    }

    public List<ImageModel> getImages() {
        return images;
    }

    @Override
    public String toString() {
        return "FieldModel{" +
                "uid='" + uid + '\'' +
                ", cultivation=" + cultivation +
                ", ground=" + ground +
                ", info=" + info +
                ", actions=" + actions +
                ", images=" + images +
                '}';
    }
}
