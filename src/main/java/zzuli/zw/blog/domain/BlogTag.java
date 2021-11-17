package zzuli.zw.blog.domain;

import java.io.Serializable;

public class BlogTag implements Serializable {
    private String id;
    private String tagId;
    private String blogId;

    public BlogTag(){}
    public BlogTag(String id, String tagId, String blogId) {
        this.id = id;
        this.tagId = tagId;
        this.blogId = blogId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    @Override
    public String toString() {
        return "BlogTag{" +
                "id='" + id + '\'' +
                ", tagId='" + tagId + '\'' +
                ", blogId='" + blogId + '\'' +
                '}';
    }
}
