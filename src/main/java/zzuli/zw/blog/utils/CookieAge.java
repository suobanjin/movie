package zzuli.zw.blog.utils;

/**
 * @ClassName: CookieAge
 * @date: 2020/7/10 22:32
 * @author 索半斤
 * @Description: 枚举类，用来枚举cookie的存活时间
 */
public enum CookieAge {
    WEEK(60*60*24*7),   //7天
    MONTH(60*60*24*30),  //一个月
    HOUR(60*60),         //一小时
    ZERO(0),            // 0，失效
    DEFAULT(-1);        //默认 -1
    private final int code;
    CookieAge(int code){
        this.code = code;
    }
    public Integer Int(){
        return this.code;
    }
}
