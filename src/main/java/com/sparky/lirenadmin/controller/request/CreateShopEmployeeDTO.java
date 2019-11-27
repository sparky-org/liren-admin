package com.sparky.lirenadmin.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @ClassName CreateShopEmployeeDTO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/18
 * @Version v0.0.1
 */
@ApiModel("创建员工请求")
public class CreateShopEmployeeDTO {

    @ApiModelProperty("岗位编号")
    private Long jobNo;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("性别：MALE/FEMALE")
    private String sex;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("生日:yyyy-MM-dd")
    private String birthday;

    @ApiModelProperty("是否管理员")
    private Boolean isAdmin;

    @ApiModelProperty("上级主管编号")
    private Long managerNo;

    @ApiModelProperty("创建人编号")
    private Long creator;

    @ApiModelProperty("店铺编号")
    private Long shopNo;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getJobNo() {
        return jobNo;
    }

    public void setJobNo(Long jobNo) {
        this.jobNo = jobNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
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

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Long getShopNo() {
        return shopNo;
    }

    public void setShopNo(Long shopNo) {
        this.shopNo = shopNo;
    }
}
