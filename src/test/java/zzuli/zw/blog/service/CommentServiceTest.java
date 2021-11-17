package zzuli.zw.blog.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zzuli.zw.blog.domain.Comment;
import zzuli.zw.blog.domain.UserAvatar;
import zzuli.zw.blog.service.interfaces.CommentService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class CommentServiceTest {
    @Autowired
    private CommentService commentService;

    @Test
    public void fun01(){
        List<Comment> allComments = commentService.findAllComments("1");
        System.out.println(Arrays.toString(allComments.toArray()));
    }

    @Test
    public void fun02(){
        Random random = new Random();
        for (int j = 0 ; j <= 100; j++) {
            int i = random.nextInt(8);
            System.out.println(i);
        }
    }

    @Test
    public void fun03() throws IOException {
        URL url = new URL("https://api.66mz8.com/api/rand.portrait.php?type=动漫&format=json");
        InputStream inputStream = url.openStream();
        String s = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        UserAvatar userAvatar = objectMapper.readValue(s, UserAvatar.class);
        System.out.println(userAvatar);
    }
}
