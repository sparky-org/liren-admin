package com.sparky.lirenadmin.controller.request;

public class CreatePointDTO {

    private Long pointConfigNo;

    private Long empNo;
    private String pointType;
    private String content;
    private Integer value;

    public Long getPointConfigNo() {
        return pointConfigNo;
    }

    public void setPointConfigNo(Long pointConfigNo) {
        this.pointConfigNo = pointConfigNo;
    }

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
