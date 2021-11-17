package zzuli.zw.blog.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zzuli.zw.blog.domain.Type;
import zzuli.zw.blog.service.interfaces.TypesService;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TypeService {
    @Autowired
    TypesService typesService;
    @Test
    public void fun01(){
        List<Type> allTypes = typesService.findAll("学习", 1, 1);
        System.out.println(Arrays.toString(allTypes.toArray()));
    }
}
