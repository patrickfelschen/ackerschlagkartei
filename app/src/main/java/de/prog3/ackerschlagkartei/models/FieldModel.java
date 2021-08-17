package de.prog3.ackerschlagkartei.models;

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

    public FieldModel(CultivationModel cultivation, GroundModel ground, InfoModel info) {
        this.uid = "";
        this.cultivation = cultivation;
        this.ground = ground;
        this.info = info;
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
}
