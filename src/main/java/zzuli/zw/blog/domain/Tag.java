package zzuli.zw.blog.domain;

import java.io.Serializable;

/**
 * @ClassName: Tag
 * @date: 2020/7/9 22:31
 * @author 索半斤
 * @Description: 文章标签实体类设计
 */
public class Tag implements Serializable {
    private String id;
    private String tagName;
    private long blogCount;
    public Tag() {
    }

    public long getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(long blogCount) {
        this.blogCount = blogCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id='" + id + '\'' +
                ", tagName='" + tagName + '\'' +
                ", blogCount=" + blogCount +
                '}';
    }
}
