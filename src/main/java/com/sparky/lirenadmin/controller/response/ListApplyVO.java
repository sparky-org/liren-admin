package com.sparky.lirenadmin.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @ClassName ListApplyVO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/12
 * @Version v0.0.1
 */
@ApiModel
public class ListApplyVO {

    @ApiModelProperty("申请编号")
    private Long applyNo;
    @ApiModelProperty("申请类型描述")
    private String applyTypeDesc;
    @ApiModelProperty("申请类型")
    private String applyType;
    @ApiModelProperty("申请状态：待审批-PENDING/已取消-CANCELED/审批通过PASSED/审批拒绝-REFUSED")
    private String status;
    @ApiModelProperty("对应申请类型-VACATION")
    private Vacation vacation;
    @ApiModelProperty("对应申请类型-SERVICE_ITEM")
    private ServiceItem serviceItem;
    @ApiModelProperty("对应申请类型-SALES_PERF")
    private SalesPerformance salesPerformance;
    @ApiModelProperty("对应申请类型-NORMAL")
    private Normal normal;

    public Long getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(Long applyNo) {
        this.applyNo = applyNo;
    }

    public String getApplyTypeDesc() {
        return applyTypeDesc;
    }

    public void setApplyTypeDesc(String applyTypeDesc) {
        this.applyTypeDesc = applyTypeDesc;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Vacation getVacation() {
        return vacation;
    }

    public void setVacation(Vacation vacation) {
        this.vacation = vacation;
    }

    public ServiceItem getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(ServiceItem serviceItem) {
        this.serviceItem = serviceItem;
    }

    public SalesPerformance getSalesPerformance() {
        return salesPerformance;
    }

    public void setSalesPerformance(SalesPerformance salesPerformance) {
        this.salesPerformance = salesPerformance;
    }

    public Normal getNormal() {
        return normal;
    }

    public void setNormal(Normal normal) {
        this.normal = normal;
    }

    @ApiModel
    public static class Vacation{
        @ApiModelProperty("开始日期")
        private Date begin;
        @ApiModelProperty("结束日期")
        private Date end;
        @ApiModelProperty("请假天数")
        private Integer days;

        public Date getBegin() {
            return begin;
        }

        public void setBegin(Date begin) {
            this.begin = begin;
        }

        public Date getEnd() {
            return end;
        }

        public void setEnd(Date end) {
            this.end = end;
        }

        public Integer getDays() {
            return days;
        }

        public void setDays(Integer days) {
            this.days = days;
        }
    }

    @ApiModel
    public static class ServiceItem{
        @ApiModelProperty("项目申请编号")
        private Long serviceRecordNo;
        @ApiModelProperty("项目名称")
        private String itemName;
        @ApiModelProperty("项目日期")
        private Date itemDate;
        @ApiModelProperty("申报积分")
        private Integer applyPoint;

        public Long getServiceRecordNo() {
            return serviceRecordNo;
        }

        public void setServiceRecordNo(Long serviceRecordNo) {
            this.serviceRecordNo = serviceRecordNo;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public Date getItemDate() {
            return itemDate;
        }

        public void setItemDate(Date itemDate) {
            this.itemDate = itemDate;
        }

        public Integer getApplyPoint() {
            return applyPoint;
        }

        public void setApplyPoint(Integer applyPoint) {
            this.applyPoint = applyPoint;
        }
    }

    @ApiModel
    public static class SalesPerformance{
        @ApiModelProperty("业绩编号")
        private Long salesPerfNo;
        @ApiModelProperty("业绩名称")
        private String name;
        @ApiModelProperty("业绩完成日期")
        private Date date;
        @ApiModelProperty("申报积分")
        private Integer applyPoint;

        public Long getSalesPerfNo() {
            return salesPerfNo;
        }

        public void setSalesPerfNo(Long salesPerfNo) {
            this.salesPerfNo = salesPerfNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Integer getApplyPoint() {
            return applyPoint;
        }

        public void setApplyPoint(Integer applyPoint) {
            this.applyPoint = applyPoint;
        }
    }

    @ApiModel
    public static class Normal{
        @ApiModelProperty("申请内容")
        private String content;
        @ApiModelProperty("申请日期")
        private Date date;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }
}
