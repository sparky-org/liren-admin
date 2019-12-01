package com.sparky.lirenadmin.entity.po;

public class PointTablePO {
    private Long empNo;
    private Integer empRank;
    private String name;
    private Integer increase;
    private Integer decrease;
    private Integer total;

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public Integer getEmpRank() {
        return empRank;
    }

    public void setEmpRank(Integer empRank) {
        this.empRank = empRank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIncrease() {
        return increase;
    }

    public void setIncrease(Integer increase) {
        this.increase = increase;
    }

    public Integer getDecrease() {
        return decrease;
    }

    public void setDecrease(Integer decrease) {
        this.decrease = decrease;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
