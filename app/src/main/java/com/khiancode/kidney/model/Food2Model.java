package com.khiancode.kidney.model;

public class Food2Model {

    private String  Id;
    private String name;
    private String eat;
    private long sodium;
    private String picture;

    public Food2Model(String id, String name,String eat, long sodium, String picture) {
        this.Id = id;
        this.name = name;
        this.eat = eat;
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

    public String getEat() {
        return eat;
    }

    public void setEat(String eat) {
        this.eat = eat;
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
