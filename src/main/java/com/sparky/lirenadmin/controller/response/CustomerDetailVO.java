package com.sparky.lirenadmin.controller.response;

import java.util.List;

/**
 * @ClassName CustomerDetailVO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/11
 * @Version v0.0.1
 */
public class CustomerDetailVO {

    private Long customerNo;
    private String name;
    private String sex;
    private String phone;
    private BaseInfo baseInfo;
    private Integer total;
    private List<TraceInfo> traceInfoList;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(Long customerNo) {
        this.customerNo = customerNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BaseInfo getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    public List<TraceInfo> getTraceInfoList() {
        return traceInfoList;
    }

    public void setTraceInfoList(List<TraceInfo> traceInfoList) {
        this.traceInfoList = traceInfoList;
    }

    public static class BaseInfo{
        private String birthDay;
        private String favor;
        private String remark;
        private String yearPlan;
        private String relatedEmp;
        private String addDate;

        public String getBirthDay() {
            return birthDay;
        }

        public void setBirthDay(String birthDay) {
            this.birthDay = birthDay;
        }

        public String getFavor() {
            return favor;
        }

        public void setFavor(String favor) {
            this.favor = favor;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getYearPlan() {
            return yearPlan;
        }

        public void setYearPlan(String yearPlan) {
            this.yearPlan = yearPlan;
        }

        public String getRelatedEmp() {
            return relatedEmp;
        }

        public void setRelatedEmp(String relatedEmp) {
            this.relatedEmp = relatedEmp;
        }

        public String getAddDate() {
            return addDate;
        }

        public void setAddDate(String addDate) {
            this.addDate = addDate;
        }
    }

    public static class TraceInfo{
        private String date;
        private String content;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}
