package zzuli.zw.blog.utils;

/**
 * @ClassName: StringUtils
 * @date: 2020/7/13 17:32
 * @author 索半斤
 * @Description: 字符串处理
 */
public class StringUtils{
    public static boolean isNullOrEmptyNotSpace(String str){
        //null
        if (str == null) return true;
        //去除空格
        str = org.springframework.util.StringUtils.trimAllWhitespace(str);
        //空字符串 ""
        return org.springframework.util.StringUtils.isEmpty(str);
    }
}
