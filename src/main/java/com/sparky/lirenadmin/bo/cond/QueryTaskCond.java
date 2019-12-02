package com.sparky.lirenadmin.bo.cond;

import java.util.List;

/**
 * @ClassName QueryTaskCond
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/8
 * @Version v0.0.1
 */
public class QueryTaskCond {

    private Long empNo;

    private String status;

    private List<Long> pointNoList;

    private int start;

    private int length;

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Long> getPointNoList() {
        return pointNoList;
    }

    public void setPointNoList(List<Long> pointNoList) {
        this.pointNoList = pointNoList;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
