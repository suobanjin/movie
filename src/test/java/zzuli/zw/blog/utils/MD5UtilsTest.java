package zzuli.zw.blog.utils;

import org.junit.jupiter.api.Test;

public class MD5UtilsTest {

    @Test
    public void fun01(){
        String s = MD5Utils.encode("8778218jia");
        System.out.println(s);
    }
}
