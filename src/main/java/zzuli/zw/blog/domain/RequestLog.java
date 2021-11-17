package zzuli.zw.blog.domain;

import java.io.Serializable;
import java.util.Arrays;

public class RequestLog implements Serializable {
    private String url;
    private String ip;
    private String classMethod;
    private Object[] objects;

    public RequestLog(String url, String ip, String classMethod, Object[] objects) {
        this.url = url;
        this.ip = ip;
        this.classMethod = classMethod;
        this.objects = objects;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClassMethod() {
        return classMethod;
    }

    public void setClassMethod(String classMethod) {
        this.classMethod = classMethod;
    }

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }

    @Override
    public String toString() {
        return "RequestLog{" +
                "url='" + url + '\'' +
                ", ip='" + ip + '\'' +
                ", classMethod='" + classMethod + '\'' +
                ", objects=" + Arrays.toString(objects) +
                '}';
    }
}
