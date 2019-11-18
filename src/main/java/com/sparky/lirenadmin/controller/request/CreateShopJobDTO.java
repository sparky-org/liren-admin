package com.sparky.lirenadmin.controller.request;

/**
 * @ClassName CreateShopJobDTO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/18
 * @Version v0.0.1
 */
public class CreateShopJobDTO {

    private Long empNo;
    private String jobName;

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
