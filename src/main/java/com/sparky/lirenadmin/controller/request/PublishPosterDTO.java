package com.sparky.lirenadmin.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName PublishNoticeDTO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/26
 * @Version v0.0.1
 */
@ApiModel
public class PublishPosterDTO {

    @ApiModelProperty("轮播图配置编号")
    private Long posterNo;
    @ApiModelProperty("操作人")
    private Long empNo;
    @ApiModelProperty("轮播图url，都好隔开")
    private String content;

    public Long getPosterNo() {
        return posterNo;
    }

    public void setPosterNo(Long posterNo) {
        this.posterNo = posterNo;
    }

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
