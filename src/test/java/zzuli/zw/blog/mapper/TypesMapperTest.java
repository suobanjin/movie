package zzuli.zw.blog.mapper;

import com.github.pagehelper.PageHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zzuli.zw.blog.domain.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TypesMapperTest {
    @Autowired
    private TypesMapper typesMapper;
    @Test
    public void fun01(){
        PageHelper.startPage(1, 1);
        List<Type> all = typesMapper.findAll("学习");
        System.out.println(Arrays.toString(all.toArray()));
    }
    @Test
    public void fun02(){
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("002");
        int i = typesMapper.deleteBatch(list);
        System.out.println(i);
    }

    @Test
    public void fun03(){
        List<Type> types = typesMapper.findTypeAndCount();
        System.out.println(Arrays.toString(types.toArray()));
    }
}
