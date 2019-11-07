package com.sparky.lirenadmin.entity;

public class CustomerInfoWithBLOBs extends CustomerInfo {
    private String remark;

    private String yearPlan;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getYearPlan() {
        return yearPlan;
    }

    public void setYearPlan(String yearPlan) {
        this.yearPlan = yearPlan == null ? null : yearPlan.trim();
    }
}