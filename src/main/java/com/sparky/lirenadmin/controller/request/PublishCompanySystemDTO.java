package com.sparky.lirenadmin.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName PublishCompanySystemDTO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/26
 * @Version v0.0.1
 */
@ApiModel
public class PublishCompanySystemDTO {

    @ApiModelProperty("制度编号（为空表示需要创建）")
    private Long articleNo;
    @ApiModelProperty("操作人")
    private Long empNo;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("内容")
    private String content;

    public Long getArticleNo() {
        return articleNo;
    }

    public void setArticleNo(Long articleNo) {
        this.articleNo = articleNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
