package com.sparky.lirenadmin.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class GetPointTableDTO {
    @ApiModelProperty("查询员工")
    private Long empNo;
    @ApiModelProperty("店铺编号")
    private Long shopNo;
    @ApiModelProperty("查询区间：DAY/MONTH/QUARTER/YEAR/DEFINE")
    private String interval;
    @ApiModelProperty("查询结束日期(interval='DEFINE'时非空：yyyy-MM-dd")
    private String beginDate;
    @ApiModelProperty("查询结束日期(interval='DEFINE'时非空：yyyy-MM-dd")
    private String endDate;

    private Integer currentPage;

    private Integer pageSize;

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

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
