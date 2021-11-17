package zzuli.zw.blog.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import zzuli.zw.blog.domain.Archives;
import zzuli.zw.blog.domain.Blog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class BlogMapperTest {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Test
    public void fun01(){
        List<Blog> blogs = blogMapper.findBlogs("如何", null, true);
        System.out.println(Arrays.toString(blogs.toArray()));
    }

    @Test
    public void fun02(){
      /*  Blog test = blogMapper.test(true);
        System.out.println(test);*/
    }

    @Test
    public void fun03(){
        Blog blog = blogMapper.findById("D3B7D0BBFF4143B1B2C7C269CB293473");
        System.out.println(blog.getContent());
    }

    @Test
    public void fun04(){
        Blog blogNotNull = blogMapper.findBlogNotNull("002");
        System.out.println(blogNotNull);
    }

    @Test
    public void fun05(){
        List<Blog> indexInfo = blogMapper.findIndexInfo(0, 6);
        System.out.println(Arrays.toString(indexInfo.toArray()));
    }

    @Test
    public void fun06(){
        List<Blog> commendBlog = blogMapper.findCommendBlog();
        System.out.println(Arrays.toString(commendBlog.toArray()));
    }

    @Test
    public void fun07(){
        Integer searchCount = blogMapper.findSearchCount("测试");
        System.out.println(searchCount);
    }

    @Test
    public void fun08(){
        List<Archives> archives = blogMapper.findArchives();
        /*Map<String,List<Archives>> map = new HashMap<>();
        for (Archives archive : archives) {
            List<Archives> list = map.get(archive.getYear());
            if (list == null){
                list = new ArrayList<>();
                list.add(archive);
                map.put(archive.getYear(),list );
            }else{
                list.add(archive);
            }
        }
        Set<Map.Entry<String, List<Archives>>> entries = map.entrySet();
        for (Map.Entry<String, List<Archives>> entry : entries) {
            System.out.println(entry.getKey()+":::"+Arrays.toString(entry.getValue().toArray()));
        }*/
        Map<String,List<Archives>> listMap = archives.stream().collect(Collectors.groupingBy(Archives::getYear));
        Set<Map.Entry<String, List<Archives>>> entries1 = listMap.entrySet();
        for (Map.Entry<String, List<Archives>> stringListEntry : entries1) {
            System.out.println(stringListEntry.getKey()+":::"+Arrays.toString(stringListEntry.getValue().toArray()));
        }
    }

    @Test
    public void fun09(){
        Blog before = blogMapper.findBefore("8989");
        System.out.println(before);
    }

    @Test
    public void fun10(){
        List<String> list = new ArrayList<>();
        list.add("0471755B42A547EDA84B026199639EC9");
        list.add("21A846C159874C009023EA18B71B88F8");
        list.add("A5B528BBC24444E899B381DBD26D4D26");
        List<Blog> blogByTags = blogMapper.findBlogByTags(list,"A5B528BBC24444E899B381DBD26D4D26");
        System.out.println(Arrays.toString(blogByTags.toArray()));
    }

    @Test
    public void fun11(){
        List<String> list = new ArrayList<>();
        list.add("0471755B42A547EDA84B026199639EC9");
        list.add("21A846C159874C009023EA18B71B88F8");
        list.add("A5B528BBC24444E899B381DBD26D4D26");
        String typeId= "84219007939B4E2FAC793E738CC795ED";
        String id = "D4314C47C20645A8B85E1CCE740E0EA9";

    }

    @Test
    public void fun12(){
        List<Blog> blogList = blogMapper.randomBlog();
        Collections.shuffle(blogList);
        List<Blog> collect = blogList.parallelStream()
                .filter(o1 -> !o1.getId().equals("123"))
                .limit(6)
                .sorted((o1, o2) -> (int) (o2.getViews() - o1.getViews()))
                .collect(Collectors.toList());
        collect.forEach(System.out::println);
    }
    /*@Test
    public void fun13(){
        jdbcTemplate.queryForObject("select * from blog", this::mapRowToBlog, "123");

    }
    private Blog mapRowToBlog(ResultSet rs,int rowNum){
        return new Blog();
    }*/
}
