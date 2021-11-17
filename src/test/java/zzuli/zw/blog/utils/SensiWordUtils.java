package zzuli.zw.blog.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zzuli.zw.blog.utils.sensiUtils.SensitiveFilter;

@SpringBootTest
public class SensiWordUtils {

    @Autowired
    private SensitiveFilter filter;
    @Test
    public void fun01() {
        // 使用默认单例（加载默认敏感词库）
        //SensitiveFilter filter = SensitiveFilter.DEFAULT;
        // 向过滤器增加一个词
        filter.put("婚礼上唱春天在哪里");

        // 待过滤的句子
        String sentence = "然后，市长在婚礼上唱春天在哪里。";
        // 进行过滤
        String felted = filter.filter(sentence, '*');

        // 如果未过滤，则返回输入的String引用
        if (!sentence.equals(felted)) {
            // 句子中有敏感词
            System.out.println(felted);
        }
    }

    @Test
    public void fun02(){
        String word = filter.filter("日你妈", '*');
        System.out.println(word);
    }
}
