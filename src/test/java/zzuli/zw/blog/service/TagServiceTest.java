package zzuli.zw.blog.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zzuli.zw.blog.domain.Tag;
import zzuli.zw.blog.service.interfaces.TagService;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TagServiceTest {
    @Autowired
    private TagService tagService;

    @Test
    public void fun01(){
        List<Tag> indexTags = tagService.findIndexTags();
        System.out.println(Arrays.toString(indexTags.toArray()));
    }

    @Test
    public void fun02(){
        List<Tag> indexTags = tagService.findIndexTags();
        System.out.println(Arrays.toString(indexTags.toArray()));
    }

    @Test
    public void fun03(){
        List<Tag> indexTags = tagService.findIndexTags();
        System.out.println(Arrays.toString(indexTags.toArray()));
    }

}
