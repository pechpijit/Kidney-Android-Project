package com.khiancode.kidney.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TaiModel extends RealmObject {
    @PrimaryKey
    String id;

    String dt;

    float value;

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

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
