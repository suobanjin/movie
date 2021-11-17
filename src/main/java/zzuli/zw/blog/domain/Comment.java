package zzuli.zw.blog.domain;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: Comment
 * @date: 2020/7/10 17:03
 * @author 索半斤
 * @Description: 评论实体类设计
 */
public class Comment {
    private String id;  //评论id
    @NotBlank
    private String nickname;  //评论人昵称
    @NotBlank
    private String email;   //评论人email
    @NotBlank
    private String content;  //评论内容
    private String avatar;   //头像
    private Date createTime;  //时间
    @NotBlank
    private String blogId;  //所属博客
    private List<String> replyCommentsId; //回复的人的id
    private List<Comment> replyComments = new ArrayList<>();
    private String parentCommentId; //上一级评论人
    private Comment parentComment; //上一级回复
    private boolean adminComment; //是否为博主
    private String parentNickname;

    public Comment() {
    }

    public String getParentNickname() {
        return parentNickname;
    }

    public void setParentNickname(String parentNickname) {
        this.parentNickname = parentNickname;
    }

    public boolean getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(boolean adminComment) {
        this.adminComment = adminComment;
    }

    public List<String> getReplyCommentsId() {
        return replyCommentsId;
    }

    public void setReplyCommentsId(List<String> replyCommentsId) {
        this.replyCommentsId = replyCommentsId;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", blogId='" + blogId + '\'' +
                ", replyCommentsId=" + replyCommentsId +
                ", replyComments=" + replyComments +
                ", parentCommentId='" + parentCommentId + '\'' +
                ", parentComment=" + parentComment +
                ", adminComment=" + adminComment +
                ", parentNickname='" + parentNickname + '\'' +
                '}';
    }
}
