package com.sparky.lirenadmin.bo.cond;

/**
 * @ClassName QueryTaskCond
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/8
 * @Version v0.0.1
 */
public class QueryTaskCond {

    private Long empNo;

    private Long pointNo;

    private int start;

    private int length;

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public Long getPointNo() {
        return pointNo;
    }

    public void setPointNo(Long pointNo) {
        this.pointNo = pointNo;
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
