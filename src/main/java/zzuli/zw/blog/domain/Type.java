package zzuli.zw.blog.domain;

import java.io.Serializable;

/**
 * @ClassName: Type
 * @date: 2020/7/9 22:31
 * @author 索半斤
 * @Description: 文章类别实体类设计
 */
public class Type implements Serializable {
    private String id;
    private String typeName;
    private int blogCount;
    public Type() {
    }

    public Type(String id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public Type(String id, String typeName, int blogCount) {
        this.id = id;
        this.typeName = typeName;
        this.blogCount = blogCount;
    }

    public int getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(int blogCount) {
        this.blogCount = blogCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id='" + id + '\'' +
                ", typeName='" + typeName + '\'' +
                ", blogCount=" + blogCount +
                '}';
    }
}
