package zzuli.zw.blog.utils;

import java.util.UUID;

/**
 * @ClassName: UUIDUtils
 * @date: 2020/7/10 22:31
 * @author 索半斤
 * @Description: 产生id的工具类
 */
public class UUIDUtils {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
