package com.sparky.lirenadmin.controller.response;

import io.swagger.annotations.ApiModel;

import java.util.Date;

/**
 * @ClassName GetEmployeeInfoVO
 * @Description TODO
 * @Author 86186
 * @Date 2019/12/3
 * @Version v0.0.1
 */
@ApiModel
public class GetEmployeeInfoVO {

    private Long empNo;

    private Long jobNo;

    private String jobName;

    private String name;

    private String sex;

    private String phone;

    private String password;

    private String avatar;

    private Integer age;

    private Date birthday;

    private Boolean isAdmin;

    private Long managerNo;

    private Integer totalPoint;

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Long getManagerNo() {
        return managerNo;
    }

    public void setManagerNo(Long managerNo) {
        this.managerNo = managerNo;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }
}
