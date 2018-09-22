package com.khiancode.kidney.model;

public class Food3Model {

    private String  Id;
    private String name;
    private String unit;
    private long sodium;
    private String picture;

    public Food3Model(String id, String name, String unit, long sodium, String picture) {
        Id = id;
        this.name = name;
        this.unit = unit;
        this.sodium = sodium;
        this.picture = picture;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public long getSodium() {
        return sodium;
    }

    public void setSodium(long sodium) {
        this.sodium = sodium;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
