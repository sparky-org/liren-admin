package com.sparky.lirenadmin.bo.cond;

/**
 * @ClassName QueryCustomerCond
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/18
 * @Version v0.0.1
 */
public class QueryCustomerCond {

    private Long shopNo;
    private Long empNo;
    private String phoneLike;
    private String nameLike;

    private Integer start;
    private Integer pageSize;

    public QueryCustomerCond(Long shopNo, Long empNo, String phoneLike, String nameLike) {
        this.shopNo = shopNo;
        this.empNo = empNo;
        this.phoneLike = phoneLike;
        this.nameLike = nameLike;
    }

    public Long getShopNo() {
        return shopNo;
    }

    public void setShopNo(Long shopNo) {
        this.shopNo = shopNo;
    }

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public String getPhoneLike() {
        return phoneLike;
    }

    public void setPhoneLike(String phoneLike) {
        this.phoneLike = phoneLike;
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
