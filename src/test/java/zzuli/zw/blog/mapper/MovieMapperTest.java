package zzuli.zw.blog.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zzuli.zw.blog.domain.Movie;

import java.util.List;

@SpringBootTest
public class MovieMapperTest {
    @Autowired
    private MovieMapper movieMapper;
    @Test
    public void tst01(){
        List<Movie> all = movieMapper.findAll();
        all.forEach(System.out::println);
    }
}
