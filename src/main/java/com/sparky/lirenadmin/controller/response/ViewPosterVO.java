package com.sparky.lirenadmin.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

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
    private List<Content> content;

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

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public static class Content{
        private String relativePath;
        private String absolutePath;

        public String getRelativePath() {
            return relativePath;
        }

        public void setRelativePath(String relativePath) {
            this.relativePath = relativePath;
        }

        public String getAbsolutePath() {
            return absolutePath;
        }

        public void setAbsolutePath(String absolutePath) {
            this.absolutePath = absolutePath;
        }
    }
}
