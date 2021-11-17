package zzuli.zw.blog.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;
import zzuli.zw.blog.domain.Blog;
import zzuli.zw.blog.service.interfaces.BlogService;

@SpringBootTest
public class MarkMdUtilsTest {
    @Autowired
    private BlogService blogService;

    @Test
    public void fun01(){
        Blog content = blogService.findBlogContentById("D3B7D0BBFF4143B1B2C7C269CB293473");
        String s = MarkdownUtils.markdownToHtml(content.getContent());
        String text = HtmlToText.convert(s);
        text = StringUtils.trimAllWhitespace(text);
        text = text.substring(0,200);
        System.out.println(text);
    }
}
