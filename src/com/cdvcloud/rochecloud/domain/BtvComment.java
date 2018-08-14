package com.cdvcloud.rochecloud.domain;

import java.util.Date;
import java.util.List;

public class BtvComment {
    private String commentId;

    private String commentName;

    private String commentContent;

    private String openId;

    private Integer status;

    private Date createTime;

    private String createUserId;

    private Date updateTime;

    private String updateUserId;

    private List<BtvCommentReply> btvCommentReplyList;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentName() {
        return commentName;
    }

    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public List <BtvCommentReply> getBtvCommentReplyList() {
        return btvCommentReplyList;
    }

    public void setBtvCommentReplyList(List <BtvCommentReply> btvCommentReplyList) {
        this.btvCommentReplyList = btvCommentReplyList;
    }
}