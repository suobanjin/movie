package zzuli.zw.blog.domain;
import java.io.Serializable;

public class UserAvatar implements Serializable {
    private int code;
    private String msg;
    String pic_url;

    @Override
    public String toString() {
        return "UserAvatar{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", pic_url='" + pic_url + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
}
