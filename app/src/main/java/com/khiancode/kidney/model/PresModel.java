package com.khiancode.kidney.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PresModel extends RealmObject {
    @PrimaryKey
    String id;

    String dt;

    float over;

    float lower;

    String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public float getOver() {
        return over;
    }

    public void setOver(float over) {
        this.over = over;
    }

    public float getLower() {
        return lower;
    }

    public void setLower(float lower) {
        this.lower = lower;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
