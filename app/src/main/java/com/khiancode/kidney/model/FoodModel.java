package com.khiancode.kidney.model;

public class FoodModel{

    private String  Id;
    private String name;
    private long sodium;
    private String picture;

//    public FoodModel(FoodModel foodModel) {
//        this.Id = foodModel.getId();
//        this.name = foodModel.getName();
//        this.sodium = foodModel.getSodium();
//        this.picture = foodModel.getPicture();
//    }

    public FoodModel(String id, String name, long sodium, String picture) {
        this.Id = id;
        this.name = name;
        this.sodium = sodium;
        this.picture = picture;
    }

    public String  getId() {
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
