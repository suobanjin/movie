package zzuli.zw.blog.utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: RegxUtils
 * @date: 2020/7/12 17:42
 * @author 索半斤
 * @Description: 正则匹配工具类
 */
public class RegxUtils {

    /**
     * @MethodName: ifHaveSpecial
     * @date: 2020/7/12 17:42
     * @author 索半斤
     * @Description: 是否包含特殊字符
     */
    public static boolean ifHaveSpecial(String str){
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p=Pattern.compile(regEx);
        Matcher m=p.matcher(str);
        return m.find();
    }
}
