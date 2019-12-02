package com.sparky.lirenadmin.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @ClassName SubmitAwardDeductionDTO
 * @Description TODO
 * @Author 86186
 * @Date 2019/12/2
 * @Version v0.0.1
 */
@ApiModel
public class SubmitAwardDeductionDTO {

    @ApiModelProperty("提交人")
    private Long submitter;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("附加照片")
    private List<String> picList;
    @ApiModelProperty("员工")
    private List<Long> empNoList;
    @ApiModelProperty("奖罚积分")
    private Integer point;

    public Long getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Long submitter) {
        this.submitter = submitter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    public List<Long> getEmpNoList() {
        return empNoList;
    }

    public void setEmpNoList(List<Long> empNoList) {
        this.empNoList = empNoList;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
