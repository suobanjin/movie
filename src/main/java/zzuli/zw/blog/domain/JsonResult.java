package zzuli.zw.blog.domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JsonResult<T> implements Serializable {
    private String msg;
    private int code;
    private List<T> data;
    private int count;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public void ok(String message){
        setCode(1);
        setMsg(message);
    }
    public void ok(String message,List<T> data){
        setCode(1);
        setMsg(message);
        setData(data);
    }
    public void ok(List<T> data){
        setCode(1);
        setData(data);
    }
    /*public void ok(T data){
        setCode(1);
        List<T> blogList = new ArrayList<>();
        blogList.add(data);
        setData(blogList);
    }*/
    public void ok(){
        setCode(1);
    }
    public void fail(){
        setCode(2);
    }
    public void fail(String message){
        setCode(2);
        setMsg(message);
    }
    @Override
    public String toString() {
        return "JsonResult{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                ", count=" + count +
                '}';
    }
}
