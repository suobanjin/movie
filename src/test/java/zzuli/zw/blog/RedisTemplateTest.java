package zzuli.zw.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisTemplateTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void fun01(){
        redisTemplate.boundHashOps("blog").put("123", "123");
        String blog = (String) redisTemplate.boundHashOps("blog").get("123");
    }

    @Test
    public void fun02(){
        String blog = (String) redisTemplate.boundHashOps("blog").get("1665648B6FA24461A9676FD09F5B9D19");
        System.out.println(blog);
    }
}
