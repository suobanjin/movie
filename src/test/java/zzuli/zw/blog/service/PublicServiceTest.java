package zzuli.zw.blog.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zzuli.zw.blog.domain.Tag;
import zzuli.zw.blog.service.interfaces.publicInterface.PublicService;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class PublicServiceTest {
    @Autowired
    private PublicService<Tag> publicService;

    @Test
    public void fun01(){
        List<Tag> all = publicService.findAll(null, 0, 0);
        System.out.println(Arrays.toString(all.toArray()));
    }
}
