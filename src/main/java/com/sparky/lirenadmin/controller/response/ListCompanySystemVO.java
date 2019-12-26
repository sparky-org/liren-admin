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
public class ListCompanySystemVO {

    @ApiModelProperty("编号")
    private Long companySystemNo;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("创建时间")
    private String createDate;

    public Long getCompanySystemNo() {
        return companySystemNo;
    }

    public void setCompanySystemNo(Long companySystemNo) {
        this.companySystemNo = companySystemNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
