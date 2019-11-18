package com.sparky.lirenadmin.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @ClassName QueryEmpGroupJob
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/18
 * @Version v0.0.1
 */
@ApiModel("员工管理查询结果")
public class QueryEmpGroupJob {

    @ApiModelProperty("岗位编号")
    private Long jobNo;
    @ApiModelProperty("岗位名称")
    private String jobName;
    @ApiModelProperty("该岗位员工")
    private List<EmpInfo> empInfos;

    public Long getJobNo() {
        return jobNo;
    }

    public void setJobNo(Long jobNo) {
        this.jobNo = jobNo;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public List<EmpInfo> getEmpInfos() {
        return empInfos;
    }

    public void setEmpInfos(List<EmpInfo> empInfos) {
        this.empInfos = empInfos;
    }

    public static class EmpInfo{
        private Long empNo;
        private String empName;
        private String phone;

        public Long getEmpNo() {
            return empNo;
        }

        public void setEmpNo(Long empNo) {
            this.empNo = empNo;
        }

        public String getEmpName() {
            return empName;
        }

        public void setEmpName(String empName) {
            this.empName = empName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
