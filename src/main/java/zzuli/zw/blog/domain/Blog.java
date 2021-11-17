package zzuli.zw.blog.domain;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: Blog
 * @date: 2020/7/9 22:26
 * @author 索半斤
 * @Description: 新闻实体类设计
 */
public class Blog implements Serializable {
    private String id;     //博客id
    @NotNull(message = "必填项不得为空")
    @NotBlank(message = "必填项不能为空")
    private String title;   //文章标题
    @NotNull
    @NotBlank
    @URL(message = "首图地址格式错误")
    private String headerImageUrl;   //首图地址
    @NotNull
    @NotBlank
    private String flag;     //标记
    private long views;    //浏览次数
    private boolean appreciation;  //是否开启赞赏
    private boolean reprintStatement; //是否开启转载声明
    private boolean comment;  //是否允许评论
    private boolean commend;  //是否推荐
    private Date createDate; //博客创建时间
    private Date updateDate; //更新时间
    private boolean save; //是否保存
    @NotBlank
    @NotNull
    private String typeId; //类别id
    private Type type; //文章类别
    private List<String> tagId; //标签id
    private List<Tag> tags; //文章标签
    private long commentCount; //评论总数
    @NotBlank
    @NotNull
    private String content;
    private String description;
    private long like;
    private String author;
    private String fromLink;
    public Blog() {
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFromLink() {
        return fromLink;
    }

    public void setFromLink(String fromLink) {
        this.fromLink = fromLink;
    }

    public long getLike() {
        return like;
    }

    public void setLike(long like) {
        this.like = like;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public boolean getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean getReprintStatement() {
        return reprintStatement;
    }

    public void setReprintStatement(boolean reprintStatement) {
        this.reprintStatement = reprintStatement;
    }

    public boolean getComment() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public boolean getCommend() {
        return commend;
    }

    public void setCommend(boolean commend) {
        this.commend = commend;
    }

    public boolean getSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<String> getTagId() {
        return tagId;
    }

    public void setTagId(List<String> tagId) {
        this.tagId = tagId;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeaderImageUrl() {
        return headerImageUrl;
    }

    public void setHeaderImageUrl(String headerImageUrl) {
        this.headerImageUrl = headerImageUrl;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", headerImageUrl='" + headerImageUrl + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", reprintStatement=" + reprintStatement +
                ", comment=" + comment +
                ", commend=" + commend +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", save=" + save +
                ", typeId='" + typeId + '\'' +
                ", type=" + type +
                ", tagId=" + tagId +
                ", tags=" + tags +
                ", commentCount=" + commentCount +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                ", like=" + like +
                ", author='" + author + '\'' +
                ", fromLink='" + fromLink + '\'' +
                '}';
    }
}