package zzuli.zw.blog.domain;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: User
 * @date: 2020/7/9 22:44
 * @author 索半斤
 * @Description: 用户实体类设计
 */
public class User implements Serializable {
    private String id; //用户id
    private String nickName;  //用户昵称
    @Size(min = 3,max = 16,message = "用户名在3到18个字符之间")
    private String username;  //用户名
    private String password;  //密码
    private String email;    //用户邮箱
    private String avatarUrl;  //头像url
    private Integer type;   //类型
    private Date createDate;  //创建时间
    private Date updateDate;  //更新时间
    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
        return "User{" +
                "id='" + id + '\'' +
                ", nickName='" + nickName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", type=" + type +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
