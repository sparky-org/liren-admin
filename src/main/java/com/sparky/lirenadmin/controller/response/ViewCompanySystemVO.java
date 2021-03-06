package com.sparky.lirenadmin.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 公司制度对象
 *
 * @ClassName ViewCompanySystemVO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/26
 * @Version v0.0.1
 */
@ApiModel
public class ViewCompanySystemVO {

    @ApiModelProperty("编号")
    private Long companySystemNo;
    @ApiModelProperty("是否可编辑")
    private Boolean canEdit;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("内容")
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCompanySystemNo() {
        return companySystemNo;
    }

    public void setCompanySystemNo(Long companySystemNo) {
        this.companySystemNo = companySystemNo;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
