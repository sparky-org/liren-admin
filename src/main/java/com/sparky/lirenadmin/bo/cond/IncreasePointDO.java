package com.sparky.lirenadmin.bo.cond;

public class IncreasePointDO {
    private String origin;
    private Long originNo;
    private Integer point;
    private Long operator;
    private Long empNo;
    private Long shopNo;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Long getOriginNo() {
        return originNo;
    }

    public void setOriginNo(Long originNo) {
        this.originNo = originNo;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public Long getShopNo() {
        return shopNo;
    }

    public void setShopNo(Long shopNo) {
        this.shopNo = shopNo;
    }
}
