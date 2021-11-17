package zzuli.zw.blog.Interceptor;

import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

public class StringTest {
    @Test
    public void fun01(){
        String str = "/admin/login/xxx";
        String myUrl = "/admin/login";
        System.out.println(str.indexOf(myUrl));

    }
}
