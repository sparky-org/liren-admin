package com.sparky.lirenadmin.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 查看轮播图
 *
 * @ClassName PublishNoticeDTO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/26
 * @Version v0.0.1
 */
@ApiModel
public class ViewPosterVO {

    @ApiModelProperty("轮播图编号")
    private Long posterNo;
    @ApiModelProperty("是否可编辑")
    private Boolean canEdit;
    @ApiModelProperty("轮播图url,逗号分隔")
    private String content;

    public Long getPosterNo() {
        return posterNo;
    }

    public void setPosterNo(Long posterNo) {
        this.posterNo = posterNo;
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
