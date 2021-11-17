package zzuli.zw.blog.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zzuli.zw.blog.domain.Blog;
import zzuli.zw.blog.domain.Page;
import zzuli.zw.blog.service.interfaces.BlogService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class BlogServiceTest {

    @Autowired
    private BlogService blogService;

    @Test
    public void fun01(){
        Page<Blog> search = blogService.findSearch(0, 6, "测试");
        System.out.println(search);
    }

    @Test
    public void fun02(){
        String[] strings = new String[3];
        strings[0] = "0471755B42A547EDA84B026199639EC9";
        strings[1] = "21A846C159874C009023EA18B71B88F8";
        strings[2] = "A5B528BBC24444E899B381DBD26D4D26";
        String typeId= "84219007939B4E2FAC793E738CC795ED";
        String id = "D4314C47C20645A8B85E1CCE740E0EA9";
        List<Blog> detailList = blogService.findDetailList(typeId, id, strings);
        System.out.println(Arrays.toString(detailList.toArray()));
    }
}
