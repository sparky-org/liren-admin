package com.sparky.lirenadmin.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 查看公告
 *
 * @ClassName PublishNoticeDTO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/26
 * @Version v0.0.1
 */
@ApiModel
public class ViewPublishNoticeVO {

    @ApiModelProperty("公告编号")
    private Long noticeNo;
    @ApiModelProperty("是否可编辑")
    private Boolean canEdit;
    @ApiModelProperty("公告内容")
    private String content;

    public Long getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(Long noticeNo) {
        this.noticeNo = noticeNo;
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
