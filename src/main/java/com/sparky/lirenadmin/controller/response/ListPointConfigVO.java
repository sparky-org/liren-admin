package com.sparky.lirenadmin.controller.response;

import com.sparky.lirenadmin.entity.PointConfig;

public class ListPointConfigVO extends PointConfig {
    private String pointTypeDesc;

    public String getPointTypeDesc() {
        return pointTypeDesc;
    }

    public void setPointTypeDesc(String pointTypeDesc) {
        this.pointTypeDesc = pointTypeDesc;
    }
}
