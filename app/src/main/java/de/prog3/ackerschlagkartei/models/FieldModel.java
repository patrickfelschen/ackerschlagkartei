package de.prog3.ackerschlagkartei.models;

public class FieldModel {
    public String uid;
    public String name;
    public String desc;

    public FieldModel() {

    }

    public FieldModel(String uid, String name, String desc) {
        this.uid = uid;
        this.name = name;
        this.desc = desc;
    }
}
