package zzuli.zw.blog.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zzuli.zw.blog.service.interfaces.BlogTagService;

import java.util.*;

@SpringBootTest
public class BlogTagServiceTest {
    @Autowired
    BlogTagService blogTagService;
    @Test
    public void fun01(){
        //第一种情况，数据库中有一个，修改了一个
        List<String> tags = new ArrayList<>();
        tags.add("34FCEA1738CE434CBB934DFFC35611A2");
        int i = blogTagService.updateTags(tags, "002");
        System.out.println(i);
    }
    @Test
    public void fun02(){
        //第二种情况，tags修改为空
        List<String> tags = new ArrayList<>();
        int i = blogTagService.updateTags(tags, "002");
        System.out.println(i);
    }
    @Test
    public void fun03(){
        //第三种情况，tags在原来的基础上增加
        List<String> tags = new ArrayList<>();
        tags.add("79BEC216D026445993400521DD8790B3");
        tags.add("CA54982D141445B39C828DCC44C53DB9");
        int i = blogTagService.updateTags(tags, "002");
        System.out.println(i);
    }
    @Test
    public void fun04(){
        //第四种情况，tags在原来的基础上减少
        List<String> tags = new ArrayList<>();
        tags.add("79BEC216D026445993400521DD8790B3");
        int i = blogTagService.updateTags(tags, "002");
        System.out.println(i);
    }
    @Test
    public void fun05(){
        //第五种情况，原先数据库中不存在
        List<String> tags = new ArrayList<>();
        tags.add("79BEC216D026445993400521DD8790B3");
        int i = blogTagService.updateTags(tags, "002");
        System.out.println(i);
    }

    @Test
    public void fun06(){
        //第六种情况，标签不存在
        List<String> tags = new ArrayList<>();
        tags.add("123");
        int i = blogTagService.updateTags(tags, "002");
        System.out.println(i);
    }
    @Test
    public void fun07(){
        //第七种情况，部分标签不存在
        List<String> tags = new ArrayList<>();
        tags.add("79BEC216D026445993400521DD8790B3");
        tags.add("123");
        int i = blogTagService.updateTags(tags, "002");
        System.out.println(i);
    }
}
