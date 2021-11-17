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
 * @Description: 电影信息实体类设计
 */
public class Movie implements Serializable {
    private String id;     //电影id
    @NotNull(message = "必填项不得为空")
    @NotBlank(message = "必填项不能为空")
    private String title;   //电影名称
    @NotNull
    @NotBlank
    @URL(message = "首图地址格式错误")
    private String headerImageUrl;   //电影海报
    private Date createDate; //博客创建时间
    private Date updateDate; //更新时间
    private String description;
    private String author;
    private String movieLink;
    public Movie() {
    }


    public String getMovieLink() {
        return movieLink;
    }

    public void setMovieLink(String movieLink) {
        this.movieLink = movieLink;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", headerImageUrl='" + headerImageUrl + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}